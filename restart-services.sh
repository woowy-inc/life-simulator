#!/bin/bash

SERVICES=(
  "gateway"
  "auth-service"
  "engine-service"
  "character-service"
  "world-service"
  "need-service"
  "notification-service"
  "eureka"
  "kafka"
  "kafka-ui"
  "redis"
  "vault"
  "postgres"
)

echo "Available services:"
for i in "${!SERVICES[@]}"; do
  echo "  $((i+1)). ${SERVICES[$i]}"
done

echo ""
read -rp "Enter service numbers to restart (space-separated): " input

selected=()
for num in $input; do
  if [[ "$num" =~ ^[0-9]+$ ]] && (( num >= 1 && num <= ${#SERVICES[@]} )); then
    selected+=("${SERVICES[$((num-1))]}")
  else
    echo "Skipping invalid number: $num"
  fi
done

if [ ${#selected[@]} -eq 0 ]; then
  echo "No valid services selected. Exiting."
  exit 1
fi

echo ""
echo "Restarting: ${selected[*]}"
for service in "${selected[@]}"; do
  (
    echo ">>> [$service] starting..."
    docker compose build "$service" && docker compose stop "$service" && docker compose up "$service" --no-deps -d
    echo ">>> [$service] done"
  ) &
done

wait
echo ""
echo "All services restarted."