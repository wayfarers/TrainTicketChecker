# Sample stats for available places by time
select 
    TicketsResponse.ticketsResponseId ID, tripDate, s1.stationName 'From', s2.stationName 'To',
    Train.trainNum, s3.stationName TrainFrom, s4.stationName TrainTill, 
    sum(p1.placesAvailable) Coupe,
    sum(p2.placesAvailable) Platz,
    sum(p3.placesAvailable) Lux
from User join UserRequest using(userId) join TicketsRequest tr using(ticketsRequestId) join TicketsResponse using(ticketsRequestId) 
join TicketsResponseItem tri using(ticketsResponseId) join Train using(trainId) join Station s1 on tr.fromStation=s1.stationId 
join Station s2 on tr.toStation=s2.stationId join Station s3 on Train.fromStation=s3.stationId
join Station s4 on Train.toStation=s4.stationId
left join Place p1 on (p1.responseItemId = tri.responseItemId and p1.placeType='COUPE') 
left join Place p2 on (p2.responseItemId = tri.responseItemId and p2.placeType='PLATZ') 
left join Place p3 on (p3.responseItemId = tri.responseItemId and p3.placeType='LUX')
where Train.trainId = 95 and tripDate='2015-12-30' group by tri.responseItemId;

