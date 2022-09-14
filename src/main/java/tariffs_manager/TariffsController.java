package tariffs_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tariffs_manager.entities.Equipment;
import tariffs_manager.entities.Extra;
import tariffs_manager.entities.Service;
import tariffs_manager.entities.Tariff;
import tariffs_manager.repositories.EquipmentRepository;
import tariffs_manager.repositories.ExtraRepository;
import tariffs_manager.repositories.ServiceRepository;
import tariffs_manager.repositories.TariffsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class TariffsController {

    @Autowired
    private TariffsRepository tariffsRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private ExtraRepository extraRepository;
    @Autowired
    private ServiceRepository serviceRepository;


    @RequestMapping(path= "/alltariffsdemo")
    public String getAllTariffsDemo(Model model){
        List<Tariff> tariffs = new ArrayList<>();
        tariffsRepository.findAll().forEach(tariffs::add);
        model.addAttribute("tariffs", tariffs);

        return "alltariffs";
    }

    @RequestMapping(path= "/alltariffs")
    public @ResponseBody Iterable<TariffResponseBody> getAllTariffs(){
        List<Tariff> tariffs = new ArrayList<>();
        tariffsRepository.findAll().forEach(tariffs::add);
        List<TariffResponseBody> bodies = new ArrayList<>();
        for(Tariff t:tariffs){
            bodies.add(setTariffBody(t));
        }
        return bodies;
    }


//    http://localhost:8080/add?name=тариф&tags=wifi,tv&type=ррррр&price=5000&short=короткий&services=1,2&extra=2,3
    private Integer lastId = -1;
    @RequestMapping(path = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody TariffResponseBody addNewTariff(
            @RequestParam(value = "name")String name,
            @RequestParam(value="tags") String tags,
            @RequestParam(value = "type") String type,
            @RequestParam(value= "price") int price,
            @RequestParam(value = "short") String Short,
            @RequestParam(value = "services") String services,
            @RequestParam(value = "equip", required = false) String equip,
            @RequestParam(value = "extra", required = false) String extra
    ){
        if(lastId == -1){
            final Iterator<Tariff> tIt = tariffsRepository.findAll().iterator();
            Tariff last = tIt.next();

            while(tIt.hasNext()){
                last = tIt.next();
            }

            lastId = last.getId();
        }


        Tariff tariff = new Tariff();
        tariff.setId(++lastId);
        tariff.setName(name);
        tariff.setTags(tags);
        tariff.setType(type);
        tariff.setPrice(price);
        tariff.setShort(Short);
        tariff.setServices(services);
        tariff.setEquip(equip);
        tariff.setExtra(extra);

        return setTariffBody(tariffsRepository.save(tariff));
    }

    @RequestMapping(path = "/addtariffdemo")
    public String addNewTariffDemo(
            @ModelAttribute Tariff tariff,
            Model model)
    {
        model.addAttribute("tariff", tariff);

        if(lastId == -1){
            final Iterator<Tariff> tIt = tariffsRepository.findAll().iterator();
            Tariff last = tIt.next();

            while(tIt.hasNext()){
                last = tIt.next();
            }

            lastId = last.getId();
        }

        tariff.setId(++lastId);

        tariffsRepository.save(tariff);
        return "addtariff";
    }

    private TariffResponseBody setTariffBody(Tariff tariff){
        TariffResponseBody.Builder builder = new TariffResponseBody.Builder(tariff.getId(), tariff.getName(), tariff.getTags().split(","),
                tariff.getType(), tariff.getPrice(), tariff.getShort());

        int [] tariffServices = Arrays.stream(tariff.getServices().split(",")).mapToInt(Integer::parseInt).toArray();
        Service[] services = new Service[tariffServices.length];
        for(int i=0;i<tariffServices.length;i++){
            services[i] = serviceRepository.findById(tariffServices[i]).get();
        }

        builder = builder.services(services);

        if(!tariff.getEquip().equals("")){
            int [] tariffsEquip = Arrays.stream(tariff.getEquip().split(",")).mapToInt(Integer::parseInt).toArray();
            Equipment[] equip = new Equipment[tariffsEquip.length];
            for(int i=0;i<tariffsEquip.length;i++){
                equip[i] = equipmentRepository.findById(tariffsEquip[i]).get();
            }
            builder = builder.equip(equip);
        }



        if(!tariff.getExtra().equals("")){
            int[] tariffsExtra = Arrays.stream(tariff.getExtra().split(",")).mapToInt(Integer::parseInt).toArray();
            Extra[] extra = new Extra[tariffsExtra.length];
            for(int i=0;i<tariffsExtra.length;i++){
                extra[i] = extraRepository.findById(tariffsExtra[i]).get();
            }

            builder = builder.extra(extra);
        }


        return builder.build();
    }
}
