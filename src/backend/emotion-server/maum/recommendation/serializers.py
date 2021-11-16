from django.db import models
from django.db.models import fields
from .models import Diary, Diary_Music, Music
from rest_framework import serializers

class DiarySerializer(serializers.ModelSerializer):
    class Meta:
        model = Diary
        fields = '__all__'

class MusicSerializer(serializers.ModelSerializer):
    class Meta:
        model = Diary_Music
        fields = '__all__'

class MusicListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Music
        fields = '__all__'