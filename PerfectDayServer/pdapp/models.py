import datetime
from django.db import models
from django.contrib.auth.models import User
from django.utils import timezone

class Event(models.Model):
    name = models.CharField(max_length = 200)
    place = models.CharField(max_length=200)
    date = models.CharField(max_length=200)
    note = models.CharField(max_length=1000)
    isFormal = models.BooleanField(default = False)
    code = models.CharField(max_length=200)
    organizer = models.ForeignKey(User)

class Firm(models.Model):
    name = models.CharField(max_length = 200)
    sector = models.CharField(max_length = 200)
    telephone = models.CharField(max_length = 200)
    email = models.CharField(max_length = 200)
    note = models.CharField(max_length = 1000)
    event = models.ForeignKey(Event)

class Guest(models.Model):
    name = models.CharField(max_length = 200)
    surname = models.CharField(max_length = 200)
    email = models.CharField(max_length=200)
    phone = models.CharField(max_length=200)
    event = models.ForeignKey(Event)

class Gift(models.Model):
    name = models.CharField(max_length = 200)
    link = models.CharField(max_length = 200)
    shop = models.CharField(max_length = 500)
    description = models.CharField(max_length = 500)
    event = models.ForeignKey(Event)
    guest = models.ForeignKey(Guest)

class Item(models.Model):
    name = models.CharField(max_length=200)
    description = models.CharField(max_length = 500)
    event = models.ForeignKey(Event)
    guest = models.ForeignKey(Guest)

class Wall(models.Model):
    author = models.CharField(max_length = 200)
    event = models.ForeignKey(Event)
    pub_date = models.DateTimeField('date published')
    note = models.CharField(max_length = 3000)

class Response(models.Model):
    author = models.CharField(max_length = 200)
    wall = models.ForeignKey(Wall)
    pub_date = models.DateTimeField('date published')
    note = models.CharField(max_length = 3000)

