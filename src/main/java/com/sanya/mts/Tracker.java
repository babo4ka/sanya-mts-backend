package com.sanya.mts;

import com.sanya.mts.tariffs_manager.entities.TrafficTracker;
import com.sanya.mts.tariffs_manager.repositories.TrafficTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;

@Controller
public class Tracker {

    @Autowired
    TrafficTrackerRepository trafficTrackerRepository;

    private Integer lastId = -1;


    @RequestMapping(path="/incvisits")
    public @ResponseBody TrafficTracker incVisits(){
        if(lastId == -1){
            final Iterator<TrafficTracker> tIt = trafficTrackerRepository.findAll().iterator();

            TrafficTracker last = tIt.hasNext()?tIt.next():null;

            if(last == null){
                lastId = 1;
            }else{
                while(tIt.hasNext()){
                    last = tIt.next();
                }

                lastId = last.getId();
            }
        }

        TrafficTracker tracker = new TrafficTracker();

        tracker.setId(++lastId);
        tracker.setDate(new Timestamp(new Date().getTime()));

        trafficTrackerRepository.save(tracker);
        return tracker;
    }


    @RequestMapping(path="/visitsinrange")
    public @ResponseBody IStats getVisitsInRange(
            @RequestParam(value="start") long start,
            @RequestParam(value="end") long end)
    {
        if(start > end) throw new IllegalArgumentException("start должен быть меньше, чем end");
        Date startDate = new Date(start);
        Date endDate = new Date(end);

        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);

        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);


        int visits = trafficTrackerRepository.findByDateBetween(
                new Timestamp(startDate.getTime()),
                new Timestamp(endDate.getTime())
        ).size();

        int days = (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));

        double avgVisits = days==0?visits:(double) visits / (double) days;

        IStats stats = new IStats() {
            @Override
            public int getVisits() {
                return visits;
            }

            @Override
            public double getAverageVisits() {
                return avgVisits;
            }
        };

        return stats;
    }

    @RequestMapping(path="/todayvisits")
    public @ResponseBody int getTodayVisits(){
        Date startToday = new Date();
        startToday.setHours(0);
        startToday.setMinutes(0);
        startToday.setSeconds(0);
        Date endToday = new Date();
        endToday.setHours(23);
        endToday.setMinutes(59);
        endToday.setSeconds(59);

        return trafficTrackerRepository.findByDateBetween(
                new Timestamp(startToday.getTime()),
                new Timestamp(endToday.getTime()))
                .size();
    }

    @RequestMapping(path="totalvisits")
    public @ResponseBody IStats totalVisists(){
        List<TrafficTracker> visits = new ArrayList<>();
        trafficTrackerRepository.findAll().forEach(visits::add);

        Date firstDay = visits.get(0).getDate();
        Date lastDay = visits.get(visits.size()-1).getDate();

        int days = (int) ((lastDay.getTime() - firstDay.getTime()) / (24 * 60 * 60 * 1000));
        double avgVisits = days==0?visits.size():(double) visits.size() / (double) days;

        IStats stats = new IStats() {
            @Override
            public int getVisits() {
                return visits.size();
            }

            @Override
            public double getAverageVisits() {
                return avgVisits;
            }
        };

        return stats;
    }
}
