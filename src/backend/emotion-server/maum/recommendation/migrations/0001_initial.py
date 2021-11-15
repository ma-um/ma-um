# Generated by Django 3.2.9 on 2021-11-09 08:37

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Song',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=20)),
                ('singer', models.CharField(max_length=40)),
                ('jacket_url', models.TextField()),
                ('fear', models.FloatField()),
                ('surprise', models.FloatField()),
                ('anger', models.FloatField()),
                ('sadness', models.FloatField()),
                ('neutrality', models.FloatField()),
                ('happiness', models.FloatField()),
                ('disgust', models.FloatField()),
                ('pleasure', models.FloatField()),
                ('embarrassment', models.FloatField()),
                ('unrest', models.FloatField()),
                ('bruise', models.FloatField()),
            ],
        ),
        migrations.CreateModel(
            name='Text',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('text', models.TextField()),
                ('result', models.TextField()),
            ],
        ),
    ]
