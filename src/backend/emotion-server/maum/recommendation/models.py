from django.db import models
from django.conf import settings
from django.db.models import fields
from django.db.models.deletion import CASCADE

# Create your models here.

class Music(models.Model):
    name = models.CharField(max_length=100)
    singer = models.CharField(max_length=100)
    jacket_url = models.TextField()
    lyric = models.TextField()
    fear = models.FloatField()
    surprise = models.FloatField()
    anger = models.FloatField()
    sadness = models.FloatField()
    neutrality = models.FloatField()
    happiness = models.FloatField()
    disgust = models.FloatField()
    pleasure = models.FloatField()
    embarrassment = models.FloatField()
    unrest = models.FloatField()
    bruise = models.FloatField()
    class Meta:
        db_table = u'music'
        # fields = ('id')


# class Diary(models.Model):
#     user_id = models.CharField(max_length=20)
#     title = models.TextField()
#     content = models.TextField()
#     registeration_time = models.DateTimeField(auto_now=True)
#     class Meta:
#         db_table = u'diary'

    
# class Diary_Music(models.Model):
#     music = models.ForeignKey(Music, on_delete=CASCADE)
#     diary = models.ForeignKey(Diary, on_delete=CASCADE)
#     class Meta:
#         db_table = u'diary_music'
