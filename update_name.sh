#!/bin/bash

ARTIFACT_NAME=$(ls target/*.war | head -n 1)
BASENAME="devops.war"
sed -i '' "s|artefacto.war|$BASENAME|" Dockerfile

echo "Archivo packer.json actualizado con el artefacto: $BASENAME"