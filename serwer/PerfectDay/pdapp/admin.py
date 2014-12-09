from django.contrib import admin
from pdapp.models import Event, Firm, Gift, Guest, Item, Response, Wall, Appointment
# Register your models here.
admin.site.register(Event)
admin.site.register(Appointment)
admin.site.register(Firm)
admin.site.register(Gift)
admin.site.register(Guest)
admin.site.register(Item)
admin.site.register(Response)
admin.site.register(Wall)