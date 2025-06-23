#!/bin/bash

APP_NAME="dompetly_api"
APP_PATH="/home/lukman/project/dompetly_api"
JAR_PATH="$APP_PATH/quarkus-run.jar"
PID_FILE="/tmp/$APP_NAME.pid"
LOG_FILE="$APP_PATH/app.log"
JAVA_PATH="/home/lukman/.sdkman/candidates/java/current/bin/java"

start() {
    echo "🚀 Starting $APP_NAME..."

    nohup "$JAVA_PATH" \
      -Dquarkus.profile=prod \
      -Dquarkus.http.host=0.0.0.0 \
      -jar "$JAR_PATH" >> "$LOG_FILE" 2>&1 &

    echo $! > "$PID_FILE"
    echo "✅ Application started with PID $(cat $PID_FILE)"
}

stop() {
    echo "🛑 Stopping $APP_NAME..."
    pid=$(cat "$PID_FILE")
    kill "$pid"
    rm -f "$PID_FILE"
    echo "✅ Application stopped"
}

restart() {
    echo "🔄 Restarting $APP_NAME..."
    stop
    sleep 2
    start
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac

exit 0
