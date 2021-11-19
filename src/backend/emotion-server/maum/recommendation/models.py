from django.db import models

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