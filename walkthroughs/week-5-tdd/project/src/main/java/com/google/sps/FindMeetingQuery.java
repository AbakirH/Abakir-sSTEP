// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.sql.Time;
import java.util.*;  

public final class FindMeetingQuery {
  private List<TimeRange> unavailableTimes = new ArrayList<>();
  private Collection<TimeRange> availableTimesForAMeeting = new ArrayList<>();
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<TimeRange> busyTimes = new ArrayList<>();
    if(events.isEmpty()){
      if(request.getDuration() > TimeRange.WHOLE_DAY.duration()){
        return Arrays.asList();
      }
      return Arrays.asList(TimeRange.WHOLE_DAY);
    }
    busyTimes = checkWhenEventsOccur(events,request);
    busyTimes = checkIfTimesOverlap(busyTimes);
    availableTimesForAttendees(busyTimes, request);
    return availableTimesForAMeeting;
  }
  public List<TimeRange> checkWhenEventsOccur(Collection<Event> events, MeetingRequest request){
    List<TimeRange> eventTimes = new ArrayList<>();
    for(Event eventItem: events ){
      for(String attendee: request.getAttendees()){
        if(eventItem.getAttendees().contains(attendee)){
          eventTimes.add(eventItem.getWhen());
        }
      }
    }
    return eventTimes;
  }
  public List<TimeRange> checkIfTimesOverlap(List<TimeRange> busyTimes){
    boolean checkIfItemWasAdded = false;
    for(int i = 0; i < busyTimes.size(); i++){
      TimeRange currentTime = busyTimes.get(i);
      TimeRange nextTimeInList;
      if(i+1 < busyTimes.size()){
        nextTimeInList =  busyTimes.get(i+1);
        if(currentTime.overlaps(nextTimeInList)){
          if(currentTime.end() > nextTimeInList.start() && currentTime.end() < nextTimeInList.end() ){
            unavailableTimes.add(TimeRange.fromStartEnd(currentTime.start(), nextTimeInList.end(), false));
            checkIfItemWasAdded = true;
          }else if(currentTime.start() <= nextTimeInList.start() &&  currentTime.end() >= nextTimeInList.end() ){
            unavailableTimes.add(TimeRange.fromStartEnd(currentTime.start(), currentTime.end(), false));
            checkIfItemWasAdded = true;
          }
      }else{
        unavailableTimes.add(busyTimes.get(i));
       }
      }else if(!checkIfItemWasAdded){
        unavailableTimes.add(busyTimes.get(i));
        checkIfItemWasAdded = false;
      }
   }
   return unavailableTimes;
  }
  public Collection<TimeRange> availableTimesForAttendees(List<TimeRange> busyTimes,MeetingRequest request) {
    int startingTime = TimeRange.START_OF_DAY;
    if(busyTimes.isEmpty()){
      availableTimesForAMeeting = Arrays.asList(TimeRange.WHOLE_DAY);
      return availableTimesForAMeeting;
    }
    for(int i = 0; i < busyTimes.size(); i++){
      TimeRange time = busyTimes.get(i);
      if(startingTime + request.getDuration() <= time.start()){
        availableTimesForAMeeting.add(TimeRange.fromStartEnd(startingTime, time.start(), false));
      }
      startingTime = time.end();
      if(i+1 >= busyTimes.size() && startingTime + request.getDuration() < TimeRange.END_OF_DAY){
        availableTimesForAMeeting.add(TimeRange.fromStartEnd(startingTime, TimeRange.END_OF_DAY, true));
      }
    }
    return availableTimesForAMeeting;
  }
}

