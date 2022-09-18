package com.sanya.mts.tariffs_manager;

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
    public @ResponseBody Map<String, Double> getVisitsInRange(
            @RequestParam(value="start") long start,
            @RequestParam(value="end") long end)
    {
        if(start > end) throw new IllegalArgumentException("start должен быть меньше, чем end");
        int visits = 0;
        Date startDate = new Date(start);
        Date endDate = new Date(end);

        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);

        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);

        Iterator<TrafficTracker> iterator = trafficTrackerRepository.findAll().iterator();

        TrafficTracker t;

        while(iterator.hasNext()){
            t = iterator.next();

            if(t.getDate().getTime() >= startDate.getTime() &&
            t.getDate().getTime() <= endDate.getTime())visits++;
        }

        int days = (int) ((endDate.getTime() - startDate.getTime())) / (24 * 60 * 60 * 1000);

        double avgVisits = days==0?visits:(double) visits / (double) days;

        Map<String, Double> stats = new HashMap<>();
        stats.put("Visits", (double)visits);
        stats.put("Average visits per day", avgVisits);

        return stats;
    }

    @RequestMapping(path="/todayvisits")
    public @ResponseBody int getTodayVisits(){
        int visits = 0;

        Date startToday = new Date();
        startToday.setHours(0);
        startToday.setMinutes(0);
        startToday.setSeconds(0);
        Date endToday = new Date();
        endToday.setHours(23);
        endToday.setMinutes(59);
        endToday.setSeconds(59);

        Iterator<TrafficTracker> iterator = trafficTrackerRepository.findAll().iterator();

        TrafficTracker t;

        while(iterator.hasNext()){
            t = iterator.next();

            if(t.getDate().getTime() >= startToday.getTime() &&
                    t.getDate().getTime() <= endToday.getTime())visits++;
        }

        return visits;
    }

    @RequestMapping(path="totalvisits")
    public @ResponseBody Map<String, Double> totalVisists(){
        List<TrafficTracker> visits = new ArrayList<>();
        trafficTrackerRepository.findAll().forEach(visits::add);

        Date firstDay = visits.get(0).getDate();
        Date lastDay = visits.get(visits.size()-1).getDate();

        int days = (int) ((lastDay.getTime() - firstDay.getTime())) / (24 * 60 * 60 * 1000);
        double avgVisits = days==0?visits.size():(double) visits.size() / (double) days;
        Map<String, Double> stats = new HashMap<>();
        stats.put("Visits", (double) visits.size());
        stats.put("Average visits per day", avgVisits);

        return stats;
    }
}
