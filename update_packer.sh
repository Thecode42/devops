#!/bin/bash

ARTIFACT_NAME=$(ls target/*.war | head -n 1)
BASENAME=$(basename "$ARTIFACT_NAME")
sed -i "s|/target/artefacto.war|/target/$BASENAME|" packer.json

echo "Archivo packer.json actualizado con el artefacto: $BASENAME"