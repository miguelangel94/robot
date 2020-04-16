package com.seat.robot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.seat.robot.report.entity.Report;

public class Calculation {

  public static Double calculateTotalDistance(String polyline) {
    final List<LatLng> trayect = polylineDecoder(polyline);
    return getTrayectTotalDistance(trayect);
  }



  public static List<LatLng> getPositionsInReportTime(final Double velocity,
      final String polyline) {
    final List<LatLng> trayect = Calculation.polylineDecoder(polyline);
    final List<Double> distanceFromOriginInReportTimesList =
        Calculation.distanceFromOriginInReportTimes(getTrayectTotalDistance(trayect), velocity);
    return distanceFromOriginInReportTimesList.stream()
        .map(position -> Calculation.positionInReportTime(trayect, position))
        .collect(Collectors.toList());
  }

  public Double calculateTotalTime(Double totalDistance, Double metersPerSecond) {
    return totalDistance / metersPerSecond;

  }

  public static int calculateAvgPollution(final List<Report> reportList) {
    int currentAvgPollution = 0;
    if (!CollectionUtils.isEmpty(reportList)) {
      final int sumPollution =
          reportList.stream().map(Report::getPm).collect(Collectors.summingInt(Integer::intValue));

      currentAvgPollution = sumPollution / reportList.size();
    }

    return currentAvgPollution;
  }

  public static int getRandomPmNumber() {
    final Random random = new Random();
    return random.nextInt(150);
  }

  private static double calculateDistanceBetweenPoints(LatLng origin, LatLng destination) {
    final double lat1 = origin.lat;
    final double lng1 = origin.lng;
    final double lat2 = destination.lat;
    final double lng2 = destination.lng;
    final double earthRadius = 6371000;
    final double dLat = Math.toRadians(lat2 - lat1);
    final double dLng = Math.toRadians(lng2 - lng1);
    final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
    final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return (float) (earthRadius * c);
  }

  private static List<Double> distanceFromOriginInReportTimes(Double totalDistance,
      Double metersPerSecond) {
    final List<Double> distanceFromOriginReportTimeList = new ArrayList<>();
    final Double metersPerReport = metersPerSecond * 900;
    Double i = metersPerReport;
    while (i < totalDistance) {
      distanceFromOriginReportTimeList.add(i);
      i = i + metersPerReport;
    }
    return distanceFromOriginReportTimeList;
  }

  private static LatLng positionInReportTime(List<LatLng> trayect, Double position) {
    Double metersFromOrigin = 0D;

    for (int i = 0; i < trayect.size() - 1; i++) {
      final Double distance = calculateDistanceBetweenPoints(trayect.get(i), trayect.get(i + 1));
      if ((metersFromOrigin + distance) > position) {
        return calculateNewLatLngBetweenTwoPoints(trayect.get(i + 1), trayect.get(i),
            ((metersFromOrigin + distance) - position));
      }
      metersFromOrigin = metersFromOrigin + distance;
    }
    return null;

  }

  private static LatLng calculateNewLatLngBetweenTwoPoints(LatLng latLngOrigin,
      LatLng latLngDestination, double extraMeters) {
    final double distanceBetweenPoints =
        calculateDistanceByCoordinates(latLngOrigin, latLngDestination);
    final LatLng newLatLng = new LatLng();
    final double extraMetersDividedByDistance = extraMeters / distanceBetweenPoints;
    newLatLng.lat = latLngOrigin.lat
        + (latLngDestination.lat - latLngOrigin.lat) * extraMetersDividedByDistance;
    newLatLng.lng = latLngOrigin.lng
        + (latLngDestination.lng - latLngOrigin.lng) * extraMetersDividedByDistance;

    return newLatLng;
  }

  private static double calculateDistanceByCoordinates(final LatLng origin,
      final LatLng destination) {
    final int Radius = 6371;// radius of earth in Km
    final double lat1 = origin.lat;
    final double lat2 = destination.lat;
    final double lon1 = origin.lng;
    final double lon2 = destination.lng;
    final double dLat = Math.toRadians(lat2 - lat1);
    final double dLon = Math.toRadians(lon2 - lon1);
    final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    final double c = 2 * Math.asin(Math.sqrt(a));

    return Radius * c * 1000;
  }

  private static List<LatLng> polylineDecoder(String polyline) {

    return PolylineEncoding.decode(polyline);
  }

  private static Double getTrayectTotalDistance(final List<LatLng> trayect) {
    Double totalDistance = 0D;

    for (int i = 0; i < trayect.size() - 1; i++) {

      totalDistance =
          totalDistance + calculateDistanceBetweenPoints(trayect.get(i), trayect.get(i + 1));
    }
    return totalDistance;
  }

}
