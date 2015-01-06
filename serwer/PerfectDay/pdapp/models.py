import datetime
from django.db import models
from django.contrib.auth.models import User
from django.db import models
from django.forms import ModelForm

class Event(models.Model):
    name = models.CharField(max_length = 200)
    place = models.CharField(max_length=200)
    date = models.CharField(max_length=200)
    note = models.CharField(max_length=1000)
    isFormal = models.BooleanField(default = False)
    code = models.CharField(max_length=200)
    organizer = models.ForeignKey(User)

class Appointment(models.Model):
    description = models.CharField(max_length=1000)
    isDone = models.BooleanField(default = False)
    event = models.ForeignKey(Event)

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
    buyer = models.CharField(max_length=200)

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

class EventForm(ModelForm):
    class Meta:
        model = Event
        fields = ['name', 'place', 'date', 'note']