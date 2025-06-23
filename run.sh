#!/bin/bash

APP_NAME="dompetly_api"
APP_PATH="/home/$USER/project/dompetly_api"
JAR_PATH="$APP_PATH/target/quarkus-app/quarkus-run.jar"
PID_FILE="/tmp/$APP_NAME.pid"
LOG_FILE="$APP_PATH/app.log"

check_pid() {
    if [ -f "$PID_FILE" ]; then
        pid=$(cat "$PID_FILE")
        if ps -p "$pid" > /dev/null 2>&1; then
            return 0
        fi
    fi
    return 1
}

start() {
    if check_pid; then
        echo "⚠️ Application is already running!"
        return 1
    fi

    echo "🚀 Starting $APP_NAME..."
    nohup java -jar \
        -Dquarkus.profile=prod \
        -Dquarkus.http.host=0.0.0.0 \
        "$JAR_PATH" > "$LOG_FILE" 2>&1 &

    echo $! > "$PID_FILE"
    echo "✅ Application started with PID $(cat $PID_FILE)"
}

stop() {
    if ! check_pid; then
        echo "⚠️ Application is not running!"
        return 1
    fi

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