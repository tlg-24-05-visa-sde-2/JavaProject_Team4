#!/bin/bash

# REPLACE TJ WITH YOUR USERNAME
DB_FILE="/Users/TJ/spring-boot-h2-db.mv.db"
TRACE_FILE="/Users/TJ/spring-boot-h2-db.trace.db"

# Check if the files exist and delete them
if [ -f "$DB_FILE" ]; then
    echo "Deleting file: $DB_FILE"
    rm "$DB_FILE"
    echo "Deleting file: $TRACE_FILE"
    rm "$TRACE_FILE"
else
    echo "File not found: $DB_FILE"
fi