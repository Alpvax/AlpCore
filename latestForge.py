#!/usr/bin/env python
import urllib2, re, HTMLParser, os
      
class ForgeParser(HTMLParser.HTMLParser):
    def __init__(self):
        HTMLParser.HTMLParser.__init__(self)
        self.vpattern = re.compile("\d+\.\d+\.\d+\.\d+")
        self.linkpattern = re.compile("\w+://[\w\./\-]*(?!.*://)")
        self.recording = 0
        self. recorded = 0
        self.rv = 0
        self.data = {"Links":{}}

    def handle_starttag(self, tag, attributes):
        if self.recorded:
            return
        if tag == 'span':
            self.rv = 1
        if self.rv and tag == 'strong':
            self.rv = 2
        
        if tag == 'i' and "fa fa-star promo-LATEST" in getAttributes(attributes, "class"):
            self.recording = 2
        #if self.recording == 1 and tag == 'div' and "links" in getAttributes(attributes, "class"):
        #    print("Links:")
        if self.recording == 1 and tag == 'a':
            map = getAttMap(attributes)
            title = getAtt(map, "title")
            link = getAtt(map, "href")
            if title and link:
                self.data["Links"][title[0]] = self.linkpattern.search(link[0]).group()

    def handle_endtag(self, tag):
        if self.recorded:
            return
        if self.recording and tag == 'div':
            self.recording -= 1
            if self.recording < 1:
                self.recorded = 1
    
    def handle_data(self, data):
        if self.recorded:
            return
        if self.rv == 2:
            self.data["MCVer"] = data
            self.rv = 0
        if self.recording and self.vpattern.match(data):
            self.data["ForgeVer"] = data
            
    def startRecording(self):
        self.recording = True
        self.currentCol = 0
        
    def endRecording(self):
        self.recording = False
        
def getAttMap(attributes):
    map = {}
    for k, v in attributes:
        if k in map:
            map[k].append(v)
        else:
            map[k] = [v]
    return map

def getAtt(attmap, key):
    if key in attmap:
        return attmap[key]
    return []
    
def getAttributes(attributes, key):
    return getAtt(getAttMap(attributes), key)

def getAttributesFromArr(attributes, key):
    return [v for k, v in attributes if k == key]

if __name__=="__main__":
    p = ForgeParser()
    response = urllib2.urlopen("http://files.minecraftforge.net/")
    html = response.read()
    p.feed(html)
    p.close()
    url = p.data["Links"]["Src"]
    fname = url.split("/")[-1]
    if os.path.exists(fname):
        print("Latest Forge developer version \"%s\" detected, not downloading new copy" % p.data["ForgeVer"])
    else:
        print("Downloading Forge developer version \"%s\" as file: \"%s\"" % (p.data["ForgeVer"], fname))
        zip = urllib2.urlopen(url)
        output = open(fname,'wb')
        output.write(zip.read())
        output.close()