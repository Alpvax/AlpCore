#!/usr/bin/env python
import urllib2, re, HTMLParser, os
      
class ForgeParser(HTMLParser.HTMLParser):
    def __init__(self, reference=None, mc_version=None, table_id="promotions_table"):
        HTMLParser.HTMLParser.__init__(self)
        ref = ""
        if mc_version:
            ver = str(mc_version).split(".")
            for i in range(len(ver)):
                if not ver[i]:
                    ver[i] = '\d'
            ref = ".".join(ver) + "-"
        if reference:
            ref += reference
        self.refpattern = re.compile(ref)
        if ref:
            print("Finding versions matching: \"%s\"" % self.refpattern.pattern)
        else:
            print("Finding all versions")
        self.table = table_id
        self.state = 0#0 if not started, 1 if in progress, 2 if finished
        self.recording = False
        self.currentTag = ""
        self.currentRef = ""
        self.currentCol = 0
        self.link = ""
        self.linkpattern = re.compile("http://adf.ly/\d+/")
        self.data = {}

    def handle_starttag(self, tag, attributes):
        if self.state == 2:
            return
        self.currentTag = tag
        if self.recording:
            for attr in attributes:
                if tag == 'table':
                    if attr[0] == 'id' and attr[1] == self.table:
                        self.state = 1
                elif tag == 'a':
                    if attr[0] == 'href':
                        self.link = attr[1]

    def handle_endtag(self, tag):
        if self.state == 2:
            return
        if tag == 'table':
            self.state = 2
        if self.recording:
            if tag == 'tr':
                self.recording = False
            if tag == 'td':
                self.currentCol += 1
    
    def handle_data(self, data):
        if self.state == 2:
            return
        if self.currentTag == 'tr':
            self.startRecording()
        elif self.currentTag == 'a' and data == '*':
            return
        elif self.recording:
            if self.currentTag == 'td':
                if self.currentCol == 0:
                    if self.refpattern.match(data):#data.startswith(self.ref):
                        self.currentRef = data
                        self.data[self.currentRef] = {'Links':{}}
                    else:
                        self.endRecording()
                if self.currentCol == 1:
                    self.data[self.currentRef]['ForgeVer'] = data
                if self.currentCol == 2:
                    self.data[self.currentRef]['MCVer'] = data
                if self.currentCol == 3:
                    self.data[self.currentRef]['Date'] = data
            elif self.currentTag == 'a' and self.currentCol == 4:
                self.data[self.currentRef]['Links'][data] = self.linkpattern.sub("", self.link)
                
    def startRecording(self):
        self.recording = True
        self.currentCol = 0
        
    def endRecording(self):
        self.recording = False

if __name__=="__main__":
    p = ForgeParser("Latest")
    response = urllib2.urlopen("http://files.minecraftforge.net/")
    html = response.read()
    p.feed(html)
    p.close()
    url = p.data["Latest"]["Links"]["Src"]
    fname = url.split("/")[-1]
    if os.path.exists(fname):
        print("Latest Forge developer version \"%s\" detected, not downloading new copy" % p.data["Latest"]["ForgeVer"])
    else:
        print("Downloading Forge developer version \"%s\" as file: \"%s\"" % (p.data["Latest"]["ForgeVer"], fname))
        zip = urllib2.urlopen(url)
        output = open(fname,'wb')
        output.write(zip.read())
        output.close()