#!/usr/bin/python

import sys
reload(sys)
sys.setdefaultencoding('utf8')

from lib import scrape
import inspect
import os
import pickle
import json
from HTMLParser import HTMLParser

session = scrape.s

class HTMLImgSrcParser(HTMLParser):
    src = ''
    def handle_starttag(self, tag, attrs):
        if 'img' == tag.lower():
            for attr in attrs:
                if 'src' == attr[0]:
                    self.src =attr[1]

class Game:
    def __init__(self):
        self.id = ''
        self.taptap_id = ''
        self.taptap_link = ''
        self.title = ''
        self.description = ''
        self.developer = ''
        self.icon = ''
        self.img = ''
        self.category = ''
        self.rating = ''
        self.rank = ''
        self.bundle = ''
        self.price = ''

    def __repr__(self):
        #        return '<Game taptap_id=%s title=%s developer=%s icon=%s img=%s category=%s rating=%s rank=%s desc=%s>' % \
        #               (self.taptap_id, self.title, self.developer, self.icon, self.img, self.category, self.rating, self.rank, self.description)
        return json.dumps(self, default=lambda o: o.__dict__)


def getBlock(candidates, attrs):
    for block in candidates:
        found = True
        for k, v_expected in attrs.iteritems():
            v_found = '' if k not in block.attrs else block.attrs[k]
            if v_expected not in v_found.split(' '):
                found &= False
                break
        if found:
            return block

    return None


def getBlocks(candidates, attrs):
    blocks = []
    for block in candidates:
        found = True
        for k,v_expected in attrs.iteritems():
            v_found = '' if k not in block.attrs else block.attrs[k]
            if v_expected not in v_found.split(' '):
                found &= False
                break
        if found:
            blocks.append(block)

    return blocks


def getLastComponent(text, delimiter):
    return text.split(delimiter)[-1]


'''
get top list section content
'''
def getTopList(html):
    sections = html.all('section')
    topListSection = getBlock(html.all('section'), {'class': 'app-top-list', 'id': 'topList'})
    return getBlocks(topListSection.all('div'), {'class': 'taptap-top-card'})

def getImgTagSrc(text):
    parser = HTMLImgSrcParser()
    parser.feed(text)
    return parser.src

def getGameDetail(top_card):
    print "start to get game detail from %s" % repr(top_card)
    game = Game()
    # icon_url
    card_left = getBlock(top_card.all('div'), {'class': 'top-card-left'})
    card_left_image = getBlock(card_left.all('a'), {'class': 'card-left-image'})
    game.taptap_link = card_left_image.attrs['href']
    game.taptap_id = getLastComponent(card_left_image.attrs['href'], '/')
    game.id = game.taptap_id
    game.icon = getImgTagSrc(card_left_image.content)

    card_middle = getBlock(top_card.all('div'), {'class': 'top-card-middle'})
    # title, developer, description
    game.title = getBlock(card_middle.all('a'), {'class': 'card-middle-title'}).first('h4').text.encode('utf8')
    game.developer = getBlock(card_middle.all('p'), {'class': 'card-middle-author'}).first('a').text.encode('utf8')
    game.description = getBlock(card_middle.all('p'), {'class': 'card-middle-description'}).text.encode('utf8')

    # category, rating
    card_footer = getBlock(card_middle.all('div'), {'class': 'card-middle-footer'})
    category_href = card_footer.first('a').attrs['href']
    category = getLastComponent(category_href, '/')
    game.category = category + '-' + card_footer.first('a').text.encode('utf8')
    try:
        game.rating = card_footer.first('span').text.encode('utf8')
    except Exception:
        pass

    # image
    card_right = getBlock(top_card.all('div'), {'class': 'top-card-right'})
    card_right_image = getBlock(card_right.all('div'), {'class': 'card-right-image'})
    game.img = getImgTagSrc(card_right_image.content)

    # rank
    game.rank = getBlock(top_card.all('span'), {'class': 'top-card-order-text'}).text.encode('utf8')

    return game

def getGameBundle(game):
    link = game.taptap_link
    print "start to get bundle from %s" % link
    try:
        html = session.go(link)
        candidates = html.all('a')
        for a in candidates:
            if 'href' in a.attrs and 'https://play.google.com/store/apps/details' in a.attrs['href']:
                game.bundle = a.attrs['href']
                game.price = a.text.encode('utf8')
                break
    except Exception:
        pass
    return game

# data = ''
# with open('sample.html', 'r') as fp:
#     data = fp.read(1024*1024)

regions = ['us', 'download', 'jp', 'kr', 'tw', 'hk']
#  download https://www.taptap.com/top/us?type=0
#  new https://www.taptap.com/top/us?type=1

games = {}
for region in regions:
    link = ('https://www.taptap.com/top/' + region)
    print 'start to get list from link %s' % link
    session.go(link)
    doc = session.doc
    # doc = scrape.Region(data)
    top_list = getTopList(doc)
    # game_list = []
    # for top_card in top_list:
    #     game = getGameDetail(top_card)
    #     print repr(game)
    #     game_list.append(game)
    game_list = [getGameDetail(top_card) for top_card in top_list]
    games[region] = game_list
    game_list = [getGameBundle(game) for game in game_list]
    with open(region, 'w') as fp:
        fp.write('{"id":"%s","details":%s}' % (region, repr(game_list)))

# print repr(games)
# print json.dumps(games)
