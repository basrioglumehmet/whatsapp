#!/bin/sh

# Değişkenler
IMAGE_NAME="spring/whatsapp"

echo "🚀 Docker imajı oluşturuluyor: $IMAGE_NAME..."

mvn clean install
# Docker imajını oluştur
docker build --no-cache -t $IMAGE_NAME .

# Başarı kontrolü
if [ $? -eq 0 ]; then
    echo "✅ Docker imajı başarıyla oluşturuldu: $IMAGE_NAME"
else
    echo "❌ Docker imajı oluşturulurken bir hata oluştu!"
    exit 1
fi
