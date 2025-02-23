#!/bin/bash

ARTIFACT_NAME=$(ls target/*.war | head -n 1)
BASENAME=$(basename "$ARTIFACT_NAME")
sed -i '' "s|artefacto.war|$BASENAME|" Dockerfile

echo "Archivo Dockerfile actualizado con el artefacto: $BASENAME"