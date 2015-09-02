require 'date'
require 'java'
#require File.dirname(__FILE__)+'./action.rb'
java_import java.util.ArrayList;
java_import java.awt.List
java_import java.util.Map;

$picturename = "picture name gloabl " 
$speed = 0
$des
$picidmap
$picID

#globle used as macro
$hit = 0


def picture
  Java::Picture
end

def applet
  Java::DrawPictureApplet
end

def show(picname, x, y, picID, timeconditiontype,  min, sec)
  $picturename = "#{picname}"
  @applet.creatPicObject("#{picname}", "#{picID}", timeconditiontype, min, sec)
  startposition(picID, x , y)
#  starttime(picID, timeconditiontype, min, sec)
end

def starttime(id, type, minute, second)
  @applet.startTime(id, type, minute, second)
end

def startposition(id, startx, starty)
  @applet.startPosition(id, startx, starty)
end

def speed(movespeed)
  $speed = movespeed
  @applet.speedConfig($picturename, movespeed)
end

def moveto(picid, desx, desy,timeconditiontype,  min, sec, speed)
  $des = desx
  @applet.straightActionConfig(picid, desx, desy , timeconditiontype, getperiod(min, sec), speed)
end

def addaction(desx, desy)
  $des = desx
  @applet.actionConfig($picturename, desx, desy)
end

def disappear(picid, timeconditiontype, min, sec)
  @applet.disappearActionConfig(picid, timeconditiontype, getperiod(min, sec) )
end

def jump(picid, desx, desy, timeconditiontype, min, sec)
  @applet.jumpActionConfig(picid, desx, desy, timeconditiontype,getperiod(min, sec))
end

def patrol(picid, desx, desy, timeconditiontype, min, sec, speed)
  @applet.patrolActionConfig(picid, desx, desy , timeconditiontype,getperiod(min, sec), speed)
end

def alter(mainpicid, conditiontype, condition)
  @applet.alterbehaviorConfig(mainpicid, conditiontype, condition)
end

def endofalter(mainpicid)
  @applet.endofalterConfig(mainpicid)
end

def futureconfig(picid)
  @applet.futureconfig(picid)
end

def mirrorreflect(picid,  timeconditiontype, min, sec)
  @applet.mirrorreflect(picid,  timeconditiontype, getperiod(min, sec))
end


def getperiod(min,sec)
  period = (min*60 + sec)*1000
  return period
end


def ruby_java_engin
#  load File.dirname(__FILE__)+'./test.rub'
#  load File.dirname(__FILE__) +"/"+ @programname.to_s
#  load "./rubydsl/" + @programname.to_s + ".rub"
  load @programname.to_s 
  return "ruby script started!"
end

ruby_java_engin
