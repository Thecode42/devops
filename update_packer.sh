#!/bin/bash

ARTIFACT_NAME=$(ls target/*.war | head -n 1)
BASENAME=$(basename "$ARTIFACT_NAME")
sed -i '' "s|artefacto.war|$BASENAME|" packer.json

echo "Archivo packer.json actualizado con el artefacto: $BASENAME"