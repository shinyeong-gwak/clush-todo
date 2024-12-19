#!/bin/bash

BASE_URL="http://localhost:8080"
USER_ID="test"
AUTH_HEADER="Content-Type: application/json"
CID= "b29e7f2d-b717-11ef-815c-0242c0a87002"
TID= "b2ab7be9-b717-11ef-815c-0242c0a87002"

function test_api() {
  local METHOD=$1
  local URL=$2
  local DATA=$3
  local DESC=$4

  if [ "$METHOD" == "GET" ] || [ "$METHOD" == "DELETE" ]; then
    RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X $METHOD "$URL")
  else
    RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X $METHOD "$URL" -H "$AUTH_HEADER" -d "$DATA")
  fi

  if [ "$RESPONSE" != "200" ]; then
    echo "❌ [$DESC] API 실패: HTTP $RESPONSE ($URL)"
  else
    echo "✅ [$DESC] API 성공"
  fi
}

# API 테스트
test_api POST "$BASE_URL/user/login" "{\"id\": \"$USER_ID\", \"pw\": \"user\"}" "로그인"
# 일정 추가 후 CID 값을 추출하여 변수에 저장
RESPONSE=$(curl -s -X POST "$BASE_URL/schedule" -H "$AUTH_HEADER" -d '{
  "userId": "'"$USER_ID"'",
  "schedule": {
    "name": "Meeting",
    "start": "2023-12-14T10:00:00",
    "end": "2023-12-14T11:00:00",
    "needNoti": true,
    "depth": 1,
    "tag": "RED"
  }
}')
CID=$(echo $RESPONSE | jq -r '.id')

echo "추출된 CID: $CID"


test_api GET "$BASE_URL/schedule/month?date=2023-12-01&userId=$USER_ID" "" "일정 조회하기"
test_api GET "$BASE_URL/schedule/day?date=2023-12-14&userId=$USER_ID" "" "일정 상세 조회하기"
test_api PUT "$BASE_URL/schedule/$CID" '{
  "userId": "'"$USER_ID"'",
  "schedule": {
    "name": "Updated Meeting",
   "start": "2023-12-14T11:00:00",
   "end": "2023-12-14T12:00:00",
   "needNoti": false,
   "depth": 2,
   "tag": "ORANGE"
   }
}' "일정 수정하기"
test_api DELETE "$BASE_URL/schedule/$CID" "" "일정 삭제하기"

RESPONSE=$(curl -s -X POST "$BASE_URL/todo" -H "$AUTH_HEADER" -d '{
  "userId": "'"$USER_ID"'",
  "todo": {
    "priority": 1,
    "name": "Buy groceries",
    "category": "personal"
  }
}')
TID=$(echo $RESPONSE | jq -r '.id')

echo "추출된 TID: $TID" 
#test_api POST "$BASE_URL/todo" '{
#  "userId": "'"$USER_ID"'",
#  "todo": {
#    "priority": "high",
#    "name": "Buy groceries",
#    "category": "personal"
#  }
#}' "할일 추가하기"


test_api PATCH "$BASE_URL/todo/$TID/complete/t" "" "할일 완료하기"
test_api PATCH "$BASE_URL/todo/$TID/complete/f" "" "할일 완료 취소하기"
test_api PATCH "$BASE_URL/todo/$TID/delay?delay=true" "" "할일 미루기"
test_api PUT "$BASE_URL/todo/$TID" '{
  "priority": 2,
  "name": "Updated Task",
  "category": "work"
}' "할일 수정하기"
test_api DELETE "$BASE_URL/todo/$TID" "" "할일 지우기"
test_api GET "$BASE_URL/todo?userId=$USER_ID" "" "할일 조회하기"
test_api GET "$BASE_URL/todo/complete?userId=$USER_ID" "" "다한 일 조회하기"
test_api GET "$BASE_URL/todo/delay?userId=$USER_ID" "" "못한 일 조회하기"

start=$(date -v+11M +"%Y-%m-%dT%H:%M:%S")
end=$(date -v+20M +"%Y-%m-%dT%H:%M:%S")
echo "$start"
test_api POST "$BASE_URL/schedule" '{
  "userId": "'"$USER_ID"'",
  "schedule": {
    "name": "Meeting",
    "start": "'"$start"'",
    "end": "'"$end"'",
    "needNoti": true,
    "depth": 1,
    "tag": "RED"
  }
}' "일정 추가하기"

echo "11분 뒤에 일정을 추가 했기 때문에 1분 안에 sse메시지 도착을 예상합니다."

curl -N -H "Accept: text/event-stream" "http://localhost:8080/sse/connect?userId=$USER_ID" | while read line; do echo $line; done