#!/usr/bin/env bash

set -e

main() {
  RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS="-setcookie aG9sYQo="

  rabbitmq-server 2> /dev/null > /dev/null &
  echo "done."
}

main
