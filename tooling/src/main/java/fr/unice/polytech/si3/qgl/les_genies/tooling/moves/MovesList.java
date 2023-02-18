package fr.unice.polytech.si3.qgl.les_genies.tooling.moves;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;

public class MovesList {

    private ArrayList<Moves> moves;

    public MovesList(String Json){
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.moves = om.readValue(Json, new TypeReference<>(){});
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<Moves> getMoves() {
        return moves;
    }
}
