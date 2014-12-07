from django.template import RequestContext
from django.shortcuts import render_to_response
from django.http import HttpResponse
import django.utils.simplejson as json
from allauth.socialaccount import providers
from allauth.socialaccount.models import SocialLogin, SocialToken, SocialApp
from allauth.socialaccount.providers.facebook.views import fb_complete_login
from allauth.socialaccount.helpers import complete_social_login
from django.views.decorators.csrf import csrf_exempt
from pdapp.models import *


def index(request):
    return render_to_response("pdapp/index.html", RequestContext(request))


@csrf_exempt
def android_checkuser(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        response_data['result'] = 'success'
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_remove(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        if (request.GET['type'] == 'event'):
            print('event')
            tmpId = int(request.GET['id'])
            Event.objects.filter(id=tmpId).delete()
            response_data['message'] = 'Pomyslnie usunieto wydarzenie'
        if (request.GET['type'] == 'guest'):
            print('guest')
            tmpId = int(request.GET['id'])
            Guest.objects.filter(id=tmpId).delete()
            response_data['message'] = 'Pomyslnie usunieto goscia'

        response_data['result'] = 'success'


        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_addguest(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['eventid'])
        tmpEvent = Event.objects.get(id=tmpId)

        print('okej')
        print(user.id)
        print(request.session.session_key)
        new_guest = Guest(name=request.GET['guestname'], surname=request.GET['guestsurname'],
                          email=request.GET['guestemail'], phone=request.GET['guesttelephone'],
                          event=tmpEvent)
        new_guest.save()
        # print(new_event)
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano goscia'
        response_data['name'] = new_guest.name
        response_data['surname'] = new_guest.surname
        response_data['email'] = new_guest.email
        response_data['phone'] = new_guest.phone
        response_data['event'] = new_guest.event.id
        response_data['id'] = new_guest.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_getallguests(request):
    print(request.COOKIES)
    response_data = {}
    response_guests = []
    if (request.user.is_authenticated()):
        tmpEvent = Event.objects.filter(id = request.GET['eventid'])
        tmpGuest = Guest.objects.filter(event = tmpEvent)
        for gu in tmpGuest:
            guestName = gu.name
            print(guestName)
            guestSurname = gu.surname
            print(guestSurname)
            guestEmail = gu.email
            print(guestEmail)
            guestTelephone = gu.phone
            print(guestTelephone)
            guestId = gu.id
            record = {'name': guestName, 'surname': guestSurname, 'email': guestEmail, 'telephone': guestTelephone, 'id': guestId}
            print record
            response_guests.append(record)

        response_data['guests'] = response_guests
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano wszystkich gosci'

        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')

@csrf_exempt
def android_getallevents(request):
    print(request.COOKIES)
    response_data = {}
    response_events = []
    if (request.user.is_authenticated()):

        tmpEvent = Event.objects.filter(organizer=request.user)
        for ev in tmpEvent:
            eventName = ev.name
            eventDate = ev.date
            eventId = ev.id
            eventPlace = ev.place
            eventNote = ev.note
            eventCode = ev.code
            record = {'name': eventName, 'id': eventId, 'date': eventDate, 'place': eventPlace, 'note': eventNote,
                      'code': eventCode}
            print record
            response_events.append(record)

        response_data['events'] = response_events
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano wszystkie wydarzenia'

        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_updateevent(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        response_data['result'] = 'success'
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_addevent(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        print('okej')
        print(user.id)
        print(request.session.session_key)
        if (request.GET['isformal'] == 1):
            new_event = Event(name=request.GET['eventname'], place=request.GET['eventplace'],
                              date=request.GET['eventdate'], note=request.GET['eventdesc'],
                              isFormal=True, code='none', organizer=request.user)
        else:
            new_event = Event(name=request.GET['eventname'], place=request.GET['eventplace'],
                              date=request.GET['eventdate'], note=request.GET['eventdesc'],
                              isFormal=False, code='none', organizer=request.user)
        new_event.save()
        # print(new_event)
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano wydarzenie'
        response_data['name'] = new_event.name
        response_data['place'] = new_event.place
        response_data['date'] = new_event.date
        response_data['description'] = new_event.note
        response_data['code'] = new_event.code
        response_data['id'] = new_event.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_login(request):
    access_token = request.GET['access_token']
    print(access_token)
    app = SocialApp.objects.get(provider='facebook')
    response_data = {}
    token = SocialToken(app=app, token=access_token)

    login = fb_complete_login(request, app, token)
    login.token = token

    login.state = SocialLogin.state_from_request(request)

    ret = complete_social_login(request, login)

    user = SocialToken.objects.get(token=token)
    print(user.account_id)

    response_data['result'] = 'success'
    response_data['message'] = 'Pomyslnie zalogowano kontem Facebook'
    response_data['login'] = request.user.username

    return HttpResponse(json.dumps(response_data), content_type='application/json')

