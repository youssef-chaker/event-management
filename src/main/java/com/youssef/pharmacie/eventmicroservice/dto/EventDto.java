package com.youssef.pharmacie.eventmicroservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
//@JsonDeserialize(using = eventDtoDeserializer.class)
public class EventDto {
    private String title;
    private String description;
    private LongLat longLat;
    private String[] tags;

    public String getPoint(){
        System.out.println("POINT ("+this.longLat.getLongitude()+" "+this.longLat.getLatitude()+")");
        return "POINT ("+this.longLat.getLongitude()+" "+this.longLat.getLatitude()+")";
    }
}

//class eventDtoDeserializer extends StdDeserializer<EventDto> {
//
//    public eventDtoDeserializer() {
//        this(null);
//    }
//
//    public eventDtoDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public EventDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//        EventDto eventDto = new EventDto();
//        var tagsNode = node.get("tags");
//        if( tagsNode.isArray()){
//            for(var n : tagsNode) {
//                eventDto.getTags().add(new Tag(n.textValue()));
//            }
//        }
//        eventDto.setTitle("blablab");
//        eventDto.setDescription("ldskjflksdjf");
//        eventDto.setLongLat(new LongLat(5,2));
//        return eventDto;
//    }
//}