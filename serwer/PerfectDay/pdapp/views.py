from django.template import RequestContext
from django.shortcuts import render_to_response
from django.http import HttpResponse
import django.utils.simplejson as json
from pip import req
from allauth.socialaccount import providers
from allauth.socialaccount.models import SocialLogin, SocialToken, SocialApp
from allauth.socialaccount.providers.facebook.views import fb_complete_login
from allauth.socialaccount.helpers import complete_social_login
from django.views.decorators.csrf import csrf_exempt
from pdapp.models import *


def index(request):
    return render_to_response("pdapp/index.html", RequestContext(request))


def android_getallgifts(request):
    print(request.COOKIES)
    response_data = {}
    response_gifts = []
    if (request.user.is_authenticated()):
        tmpEvent = Event.objects.filter(id = request.GET['eventid'])
        tmpGift = Gift.objects.filter(event = tmpEvent)
        for a in tmpGift:
            aName = a.name
            aLink = a.link
            aShop = a.shop
            aDescription = a.description
            aBuyer = a.buyer
            aId = a.id
            record = {'name': aName, 'link': aLink, 'shop': aShop, 'description': aDescription, 'buyer': aBuyer, 'id': aId}
            print record
            response_gifts.append(record)

        response_data['gifts'] = response_gifts
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano wszystkie pozycje'

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
def android_addgift(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['eventid'])
        tmpEvent = Event.objects.get(id=tmpId)

        print('okej')
        print(user.id)
        print(request.session.session_key)
        new_gift = Gift(name=request.GET['name'], link=request.GET['link'], shop=request.GET['shop'],
                        description=request.GET['description'], event=tmpEvent, buyer=request.GET['buyer'])
        new_gift.save()
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano pozycje'
        response_data['name'] = new_gift.name
        response_data['link'] = new_gift.link
        response_data['shop'] = new_gift.shop
        response_data['description'] = new_gift.description
        response_data['buyer'] = new_gift.buyer
        response_data['id'] = new_gift.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_getallnotes(request):
    print(request.COOKIES)
    response_data = {}
    response_contacts = []
    if (request.user.is_authenticated()):
        tmpEvent = Event.objects.filter(id = request.GET['eventid'])
        tmpNote = Appointment.objects.filter(event = tmpEvent)
        for co in tmpNote:
            noDescription = co.description
            noIsdone = co.isDone
            coId = co.id
            record = {'description': noDescription, 'isdone': noIsdone, 'id': coId}
            print record
            response_contacts.append(record)

        response_data['notes'] = response_contacts
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano wszystkie notatki'

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
def android_addnote(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['eventid'])
        tmpEvent = Event.objects.get(id=tmpId)

        print('okej')
        print(user.id)
        print(request.session.session_key)
        new_note = Appointment(description=request.GET['note'], isDone=False, event=tmpEvent)
        new_note.save()
        # print(new_event)
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano notatke'
        response_data['description'] = new_note.description
        response_data['isdone'] = new_note.isDone
        response_data['id'] = new_note.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_getallcontacts(request):
    print(request.COOKIES)
    response_data = {}
    response_contacts = []
    if (request.user.is_authenticated()):
        tmpEvent = Event.objects.filter(id = request.GET['eventid'])
        tmpContact = Firm.objects.filter(event = tmpEvent)
        for co in tmpContact:
            coName = co.name
            coSector = co.sector
            coEmail = co.email
            coTelephone = co.telephone
            coNote = co.note
            coId = co.id
            record = {'name': coName, 'sector': coSector, 'email': coEmail, 'telephone': coTelephone, 'note': coNote, 'id': coId}
            print record
            response_contacts.append(record)

        response_data['contacts'] = response_contacts
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano wszystkie kontakty'

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
def android_addcontact(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['eventid'])
        tmpEvent = Event.objects.get(id=tmpId)

        print('okej')
        print(user.id)
        print(request.session.session_key)
        new_contact = Firm(name=request.GET['name'], sector=request.GET['sector'],
                          telephone=request.GET['telephone'], email=request.GET['email'],
                          note=request.GET['note'], event=tmpEvent)
        new_contact.save()
        # print(new_event)
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano kontakt'
        response_data['name'] = new_contact.name
        response_data['sector'] = new_contact.sector
        response_data['email'] = new_contact.email
        response_data['telephone'] = new_contact.telephone
        response_data['note'] = new_contact.note
        response_data['id'] = new_contact.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


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
        if (request.GET['type'] == 'contact'):
            print('guest')
            tmpId = int(request.GET['id'])
            Firm.objects.filter(id=tmpId).delete()
            response_data['message'] = 'Pomyslnie usunieto kontakt'
        if (request.GET['type'] == 'note'):
            tmpId = int(request.GET['id'])
            Appointment.objects.filter(id=tmpId).delete()
            response_data['message'] = 'Pomyslnie usunieto notatke'
        if (request.GET['type'] == 'gift'):
            tmpId = int(request.GET['id'])
            Gift.objects.filter(id=tmpId).delete()
            response_data['message'] = 'Pomyslnie usunieto pozycje'

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

