from django.template import RequestContext
from django.shortcuts import render_to_response, render
from django.http import HttpResponse
import django.utils.simplejson as json
from allauth.socialaccount.models import SocialLogin, SocialToken, SocialApp
from allauth.socialaccount.providers.facebook.views import fb_complete_login
from allauth.socialaccount.helpers import complete_social_login
from django.views.decorators.csrf import csrf_exempt
from pdapp.models import *
from datetime import datetime
import random
from django.utils.crypto import get_random_string
from pdapp.forms import EventForm


def index(request):
    return render_to_response("pdapp/index.html", RequestContext(request))


@csrf_exempt
def gencode(request):

        tmpEvent = Event.objects.filter(organizer=request.user)
        return render(request, 'pdapp/selectevent.html', {'events': tmpEvent})

# if a GET (or any other method) we'll create a blank form
        #return render(request, 'pdapp/selectevent.html', {'events': tmpEvent})



@csrf_exempt
def android_signupgift(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['id'])
        tmpGift = Gift.objects.get(id=tmpId)

        tmpGift.buyer = request.GET['buyer']
        tmpGift.save()

        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie zapisano na pozycje'
        response_data['buyer'] = tmpGift.buyer
        response_data['id'] = tmpGift.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_guestlogin(request):
    print(request.COOKIES)
    response_data = {}
    try:
        if (request.user.is_authenticated()):
            tmpEvent = Event.objects.get(code=request.GET['code'])
            coName = tmpEvent.name
            coPlace = tmpEvent.place
            coDate = tmpEvent.date
            coNote = tmpEvent.note
            coCode = tmpEvent.code
            coOrganizer = tmpEvent.organizer.username
            coId = tmpEvent.id

            response_data['result'] = 'success'
            response_data['message'] = 'Pomyslnie pobrano szczegoly wydarzenia'
            response_data['name'] = coName
            response_data['place'] = coPlace
            response_data['date'] = coDate
            response_data['note'] = coNote
            response_data['code'] = coCode
            response_data['organizer'] = coOrganizer
            response_data['id'] = coId

            print('okej')
            user = request.user
            print(user.id)
            print(request.session.session_key)
            return HttpResponse(json.dumps(response_data), content_type='application/json')
    except Exception:
        print('zle')
        response_data['result'] = 'failure'
        response_data['message'] = 'Nie ma takiego wydarzenia!'
        response_data['name'] = ''
        response_data['place'] = ''
        response_data['date'] = ''
        response_data['note'] = ''
        response_data['code'] = ''
        response_data['organizer'] = ''
        response_data['id'] = ''
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        response_data['message'] = 'Blad!'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_getwall(request):
    print(request.COOKIES)
    response_data = {}
    response_wall = []
    if (request.user.is_authenticated()):
        tmpEvent = Event.objects.filter(id=request.GET['eventid'])
        tmpWall = Wall.objects.filter(event=tmpEvent)
        for a in tmpWall:
            aNote = a.note
            aAuthor = a.author
            aDate = str(a.pub_date)
            aId = a.id
            aResponses = []
            tmpResponses = Response.objects.filter(wall=a)
            for b in tmpResponses:
                bNote = b.note
                bAuthor = b.author
                bDate = str(b.pub_date)
                bId = b.id
                bWallid = a.id
                resp = {'id': bId, 'note': bNote, 'author': bAuthor, 'date': bDate, 'wallid': bWallid}
                aResponses.append(resp)

            record = {'id': aId, 'note': aNote, 'author': aAuthor, 'date': aDate, 'responses': aResponses}
            print record
            response_wall.append(record)

        response_data['wall'] = response_wall
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano cala dyskusje'

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
def android_addresponse(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['wallid'])
        tmpWall = Wall.objects.get(id=tmpId)

        print('okej')
        print(user.id)
        print(request.session.session_key)
        new_response = Response(author=request.GET['author'], wall=tmpWall, pub_date=datetime.now(),
                                note=request.GET['note'])
        new_response.save()
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano odpowiedz'
        response_data['note'] = new_response.note
        response_data['author'] = new_response.author
        date = str(new_response.pub_date)
        response_data['date'] = date
        response_data['wallid'] = tmpId
        response_data['id'] = new_response.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_addwall(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        tmpId = int(request.GET['eventid'])
        tmpEvent = Event.objects.get(id=tmpId)

        print('okej')
        print(user.id)
        print(request.session.session_key)
        new_wall = Wall(author=request.GET['author'], event=tmpEvent, pub_date=datetime.now(),
                        note=request.GET['note'])
        new_wall.save()
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie dodano watek'
        response_data['note'] = new_wall.note
        response_data['author'] = new_wall.author
        date = str(new_wall.pub_date)
        response_data['date'] = date
        response_data['id'] = new_wall.id

        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def android_getallgifts(request):
    print(request.COOKIES)
    response_data = {}
    response_gifts = []
    if (request.user.is_authenticated()):
        tmpEvent = Event.objects.filter(id=request.GET['eventid'])
        tmpGift = Gift.objects.filter(event=tmpEvent)
        for a in tmpGift:
            aName = a.name
            aLink = a.link
            aShop = a.shop
            aDescription = a.description
            aBuyer = a.buyer
            aId = a.id
            record = {'name': aName, 'link': aLink, 'shop': aShop, 'description': aDescription, 'buyer': aBuyer,
                      'id': aId}
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
        tmpEvent = Event.objects.filter(id=request.GET['eventid'])
        tmpNote = Appointment.objects.filter(event=tmpEvent)
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
        tmpEvent = Event.objects.filter(id=request.GET['eventid'])
        tmpContact = Firm.objects.filter(event=tmpEvent)
        for co in tmpContact:
            coName = co.name
            coSector = co.sector
            coEmail = co.email
            coTelephone = co.telephone
            coNote = co.note
            coId = co.id
            record = {'name': coName, 'sector': coSector, 'email': coEmail, 'telephone': coTelephone, 'note': coNote,
                      'id': coId}
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
        tmpEvent = Event.objects.filter(id=request.GET['eventid'])
        tmpGuest = Guest.objects.filter(event=tmpEvent)
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
            record = {'name': guestName, 'surname': guestSurname, 'email': guestEmail, 'telephone': guestTelephone,
                      'id': guestId}
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
def android_addevent(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        user = request.user
        print('okej')
        print(user.id)
        print(request.session.session_key)
        letters = get_random_string(length=3, allowed_chars='abcdefghijklmnopqrstuvwxyz')
        numbers = get_random_string(length=2, allowed_chars='0123456789')
        tmpCode = u'' + letters + numbers

        l = list(tmpCode)
        random.shuffle(l)
        random_number = ''.join(l)

        while Event.objects.filter(code=random_number):
            letters = get_random_string(length=3, allowed_chars='abcdefghijklmnopqrstuvwxyz')
            numbers = get_random_string(length=2, allowed_chars='0123456789')
            tmpCode = u'' + letters + numbers

            l = list(tmpCode)
            random.shuffle(l)
            random_number = ''.join(l)

        if (request.GET['isformal'] == 1):
            new_event = Event(name=request.GET['eventname'], place=request.GET['eventplace'],
                              date=request.GET['eventdate'], note=request.GET['eventdesc'],
                              isFormal=True, code=random_number, organizer=request.user)
        else:
            new_event = Event(name=request.GET['eventname'], place=request.GET['eventplace'],
                              date=request.GET['eventdate'], note=request.GET['eventdesc'],
                              isFormal=False, code=random_number, organizer=request.user)
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


@csrf_exempt
def web_addguest(request):
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
def web_removeevent(request):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        if (request.GET['type'] == 'event'):
            print('event')
            tmpId = int(request.GET['id'])
            Event.objects.filter(id=tmpId).delete()
            response_data['message'] = 'Pomyslnie usunieto wydarzenie'
            tmpEvent = Event.objects.filter(organizer=request.user)
            error = False;
            return render(request, 'pdapp/selectevent.html', {'error': error, 'events': tmpEvent})
        else:
            return render(request, 'pdapp/error.html')
    else:
        return render(request, 'pdapp/error.html')



@csrf_exempt
def web_getwall(request, eventid):
    print(request.COOKIES)
    response_data = {}
    response_wall = []
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.filter(id=tmpId)
        tmpWall = Wall.objects.filter(event=tmpEvent)
        for a in tmpWall:
            aNote = a.note
            aAuthor = a.author
            aDate = str(a.pub_date)
            aId = a.id
            aResponses = []
            tmpResponses = Response.objects.filter(wall=a)
            for b in tmpResponses:
                bNote = b.note
                bAuthor = b.author
                bDate = str(b.pub_date)
                bId = b.id
                bWallid = a.id
                resp = {'id': bId, 'note': bNote, 'author': bAuthor, 'date': bDate, 'wallid': bWallid}
                aResponses.append(resp)

            record = {'id': aId, 'note': aNote, 'author': aAuthor, 'date': aDate, 'responses': aResponses}
            print record
            response_wall.append(record)

        response_data['wall'] = response_wall
        response_data['result'] = 'success'
        response_data['message'] = 'Pomyslnie pobrano cala dyskusje'

        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        return render(request, 'pdapp/wall.html', {'ev': tmpId, 'wall': response_wall})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))


@csrf_exempt
def web_getallgifts(request, eventid):
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.filter(id=tmpId)
        tmpGift = Gift.objects.filter(event=tmpEvent)

        return render(request, 'pdapp/gifts.html', {'ev': tmpId, 'gifts': tmpGift})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))


@csrf_exempt
def web_getallnotes(request, eventid):
    print(request.COOKIES)
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.filter(id=tmpId)
        tmpNote = Appointment.objects.filter(event=tmpEvent)

        return render(request, 'pdapp/notes.html', {'ev': tmpId, 'notes': tmpNote})
    else:
        print('zle')
        return render_to_response("pdapp/error.html", RequestContext(request))


@csrf_exempt
def web_getallcontacts(request, eventid):
    print(request.COOKIES)
    response_data = {}
    response_contacts = []
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)
        tmpContact = Firm.objects.filter(event=tmpEvent)

        return render(request, 'pdapp/contacts.html', {'ev': tmpId, 'contacts': tmpContact})
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def web_getevent(request, eventid):
    print(request.COOKIES)
    response_data = {}
    response_events = []
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)

        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        return render(request, 'pdapp/summary.html', {'ev': tmpId, 'event': tmpEvent})
    else:
        print('zle')
        response_data['result'] = 'failure'
        return render_to_response("pdapp/error.html", RequestContext(request))


@csrf_exempt
def web_getallguests(request, eventid):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.filter(id=tmpId)
        tmpGuest = Guest.objects.filter(event=tmpEvent)

        user = request.user
        print(user.id)
        print(request.session.session_key)
        return render(request, 'pdapp/guestlist.html', {'ev': tmpId, 'guests': tmpGuest})
    else:
        print('zle')
        return HttpResponse(json.dumps(response_data), content_type='application/json')


@csrf_exempt
def web_getallevents(request):
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
        return render(request, 'pdapp/selectevent.html', {'events': tmpEvent})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))

@csrf_exempt
def web_removeguest(request, eventid):
    print(request.COOKIES)
    if (request.user.is_authenticated()):
        if (request.GET['type'] == 'guest'):
            tmpId = int(eventid)
            tmpEvent = Event.objects.get(id=tmpId)
            Guest.objects.filter(id=request.GET['id']).delete()
            error = False;
            tmpGuest = Guest.objects.filter(event=tmpEvent)
            return render(request, 'pdapp/guestlist.html', {'ev': tmpId, 'guests': tmpGuest, 'error': error})
        else:
            return render(request, 'pdapp/error.html')
    else:
        return render(request, 'pdapp/error.html')

@csrf_exempt
def web_removecontact(request, eventid):
    print(request.COOKIES)
    if (request.user.is_authenticated()):
        if (request.GET['type'] == 'contact'):
            tmpId = int(eventid)
            tmpEvent = Event.objects.get(id=tmpId)
            Firm.objects.filter(id=request.GET['id']).delete()
            error = False;
            tmpContact = Firm.objects.filter(event=tmpEvent)
            return render(request, 'pdapp/contacts.html', {'ev': tmpId, 'contacts': tmpContact, 'error': error})
        else:
            return render(request, 'pdapp/error.html')
    else:
        return render(request, 'pdapp/error.html')

@csrf_exempt
def web_removenote(request, eventid):
    print(request.COOKIES)
    if (request.user.is_authenticated()):
        if (request.GET['type'] == 'note'):
            tmpId = int(eventid)
            tmpEvent = Event.objects.get(id=tmpId)
            Appointment.objects.filter(id=request.GET['id']).delete()
            error = False;
            tmpNote = Appointment.objects.filter(event=tmpEvent)
            return render(request, 'pdapp/notes.html', {'ev': tmpId, 'notes': tmpNote, 'error': error})
        else:
            return render(request, 'pdapp/error.html')
    else:
        return render(request, 'pdapp/error.html')

@csrf_exempt
def web_removegift(request, eventid):
    print(request.COOKIES)
    if (request.user.is_authenticated()):
        if (request.GET['type'] == 'gift'):
            tmpId = int(eventid)
            tmpEvent = Event.objects.get(id=tmpId)
            Gift.objects.filter(id=request.GET['id']).delete()
            error = False;
            tmpGift = Gift.objects.filter(event=tmpEvent)
            return render(request, 'pdapp/gifts.html', {'ev': tmpId, 'gifts': tmpGift, 'error': error})
        else:
            return render(request, 'pdapp/error.html')
    else:
        return render(request, 'pdapp/error.html')


@csrf_exempt
def web_addresponse(request, eventid):
    response_wall = []
    if (request.user.is_authenticated()):
        tmpId2 = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId2)
        tmpId = int(request.GET['wallid'])
        tmpWall2 = Wall.objects.get(id=tmpId)
        error = False
        if 'note' in request.GET:
            n = request.GET['note']
            if not n:
                error = True
            else:
                new_response = Response(author=request.user, wall=tmpWall2, pub_date=datetime.now(),
                                note=request.GET['note'])
                new_response.save()

        tmpWall = Wall.objects.filter(event=tmpEvent)
        for a in tmpWall:
            aNote = a.note
            aAuthor = a.author
            aDate = str(a.pub_date)
            aId = a.id
            aResponses = []
            tmpResponses = Response.objects.filter(wall=a)
            for b in tmpResponses:
                bNote = b.note
                bAuthor = b.author
                bDate = str(b.pub_date)
                bId = b.id
                bWallid = a.id
                resp = {'id': bId, 'note': bNote, 'author': bAuthor, 'date': bDate, 'wallid': bWallid}
                aResponses.append(resp)

            record = {'id': aId, 'note': aNote, 'author': aAuthor, 'date': aDate, 'responses': aResponses}
            print record
            response_wall.append(record)
        return render(request, 'pdapp/wall.html', {'ev': tmpId2, 'wall': response_wall, 'error': error})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))

@csrf_exempt
def web_addwall(request, eventid):
    response_wall = []
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)
        error = False

        if 'note' in request.GET:
            n = request.GET['note']
            if not n:
                error = True
            else:
                new_wall = Wall(author=request.user, event=tmpEvent, pub_date=datetime.now(),
                        note=request.GET['note'])
                new_wall.save()

        tmpWall = Wall.objects.filter(event=tmpEvent)
        for a in tmpWall:
            aNote = a.note
            aAuthor = a.author
            aDate = str(a.pub_date)
            aId = a.id
            aResponses = []
            tmpResponses = Response.objects.filter(wall=a)
            for b in tmpResponses:
                bNote = b.note
                bAuthor = b.author
                bDate = str(b.pub_date)
                bId = b.id
                bWallid = a.id
                resp = {'id': bId, 'note': bNote, 'author': bAuthor, 'date': bDate, 'wallid': bWallid}
                aResponses.append(resp)

            record = {'id': aId, 'note': aNote, 'author': aAuthor, 'date': aDate, 'responses': aResponses}
            print record
            response_wall.append(record)
        return render(request, 'pdapp/wall.html', {'ev': tmpId, 'wall': response_wall, 'error': error})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))


@csrf_exempt
def web_addgift(request, eventid):
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)
        error = False

        if 'name' in request.GET:
            n = request.GET['name']
            if not n:
                error = True
            else:
                new_gift = Gift(name=request.GET['name'], link=request.GET['link'], shop=request.GET['shop'],
                                description=request.GET['description'], event=tmpEvent, buyer=request.GET['buyer'])
                new_gift.save()
        tmpGift = Gift.objects.filter(event=tmpEvent)
        return render(request, 'pdapp/gifts.html', {'ev': tmpId, 'gifts': tmpGift, 'error': error})
    else:
        return render(request, 'pdapp/error.html')

@csrf_exempt
def web_addnote(request, eventid):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)
        error = False

        if 'note' in request.GET:
            n = request.GET['note']
            if not n:
                error = True
            else:
                new_note = Appointment(description=request.GET['note'], isDone=False, event=tmpEvent)
                new_note.save()
        tmpNote = Appointment.objects.filter(event=tmpEvent)
        return render(request, 'pdapp/notes.html', {'ev': tmpId, 'notes': tmpNote, 'error': error})
    else:
        return render(request, 'pdapp/error.html')

@csrf_exempt
def web_addcontact(request, eventid):
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)
        error = False
        if 'name' in request.GET:
            n = request.GET['name']
            if not n:
                error = True
            else:
                new_contact = Firm(name=request.GET['name'], sector=request.GET['sector'],
                                   telephone=request.GET['telephone'], email=request.GET['email'],
                                   note=request.GET['note'], event=tmpEvent)
                new_contact.save()

        tmpContact = Firm.objects.filter(event=tmpEvent)
        return render(request, 'pdapp/contacts.html', {'ev': tmpId, 'contacts': tmpContact, 'error': error})
    else:
        return render(request, 'pdapp/error.html')


@csrf_exempt
def web_addguest(request, eventid):
    print(request.COOKIES)
    response_data = {}
    if (request.user.is_authenticated()):
        tmpId = int(eventid)
        tmpEvent = Event.objects.get(id=tmpId)
        error = False
        if 'guestname' in request.GET and 'guestsurname' in request.GET:
            n = request.GET['guestname']
            s = request.GET['guestsurname']
            if not n or not s:
                error = True
            else:

                new_guest = Guest(name=request.GET['guestname'], surname=request.GET['guestsurname'],
                                  email=request.GET['guestemail'], phone=request.GET['guesttelephone'],
                                  event=tmpEvent)
                new_guest.save()

        tmpGuest = Guest.objects.filter(event=tmpEvent)
        return render(request, 'pdapp/guestlist.html', {'ev': tmpId, 'guests': tmpGuest, 'error': error})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))

@csrf_exempt
def web_addevent(request):
    print(request.COOKIES)
    if (request.user.is_authenticated()):
        error = False
        if 'name' in request.GET:
            n = request.GET['name']
            p = request.GET['place']
            d = request.GET['date']
            nt = request.GET['desc']
            if not n or not p or not d or not nt:
                error = True
            else:
                user = request.user
                print('okej')
                print(user.id)
                print(request.session.session_key)
                letters = get_random_string(length=3, allowed_chars='abcdefghijklmnopqrstuvwxyz')
                numbers = get_random_string(length=2, allowed_chars='0123456789')
                tmpCode = u'' + letters + numbers

                l = list(tmpCode)
                random.shuffle(l)
                random_number = ''.join(l)

                while Event.objects.filter(code=random_number):
                    letters = get_random_string(length=3, allowed_chars='abcdefghijklmnopqrstuvwxyz')
                    numbers = get_random_string(length=2, allowed_chars='0123456789')
                    tmpCode = u'' + letters + numbers

                    l = list(tmpCode)
                    random.shuffle(l)
                    random_number = ''.join(l)


                new_event = Event(name=request.GET['name'], place=request.GET['place'],
                                      date=request.GET['date'], note=request.GET['desc'],
                                      isFormal=False, code=random_number, organizer=request.user)
                new_event.save()

        tmpEvent = Event.objects.filter(organizer=request.user)
        return render(request, 'pdapp/selectevent.html', {'error': error, 'events': tmpEvent})
    else:
        return render_to_response("pdapp/error.html", RequestContext(request))
