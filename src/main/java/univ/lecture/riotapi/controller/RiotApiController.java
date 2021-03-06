package univ.lecture.riotapi.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;
import univ.lecture.riotapi.Calculator;
import univ.lecture.riotapi.model.Summoner;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1/calc")
@Log4j
public class RiotApiController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

 /*   @RequestMapping(value = "/summoner/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Summoner querySummoner(@PathVariable("name") String summonerName) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint + "/summoner/by-name/" +
                summonerName +
                "?api_key=" +
                riotApiKey;

        String response = restTemplate.getForObject(url, String.class);
        Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);

        parsedMap.forEach((key, value) -> log.info(String.format("key [%s] type [%s] value [%s]", key, value.getClass(), value)));

        Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
        String queriedName = (String)summonerDetail.get("name");
        int queriedLevel = (Integer)summonerDetail.get("summonerLevel");
        Summoner summoner = new Summoner(queriedName, queriedLevel);

        return summoner;
    }*/
    @RequestMapping(value = "/calc/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint;                   
        Calculator calc=new Calculator();
        
        int teamId = 7;
        long now = System.currentTimeMillis(); 
        double result = calc.calculate(equation);
                                                       
        Summoner summoner = new Summoner(teamId,now,result);
        Gson gson = new Gson();
        //String request = gson.toJson(summoner);
        String string = restTemplate.postForObject(url, summoner, String.class);
        return string;
        //String  = new Summoner(teamId,now,result,msg);
    }
      


}
