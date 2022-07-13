package rzeznik.grzegorz.exotic_farm;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rzeznik.grzegorz.exotic_farm.animal.Sex;
import rzeznik.grzegorz.exotic_farm.animal.Temperament;
import rzeznik.grzegorz.exotic_farm.animal.spider.*;
import rzeznik.grzegorz.exotic_farm.animal.spider.molt.MoltRepository;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpeciesInfoDAO;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpeciesInfoFromFileDTO;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpiderSpeciesInfo;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpiderSpeciesInfoRepository;
import rzeznik.grzegorz.exotic_farm.care.CareRepository;
import rzeznik.grzegorz.exotic_farm.farm.Farm;
import rzeznik.grzegorz.exotic_farm.farm.FarmRepository;
import rzeznik.grzegorz.exotic_farm.user.Role;
import rzeznik.grzegorz.exotic_farm.user.RoleRepository;
import rzeznik.grzegorz.exotic_farm.user.User;
import rzeznik.grzegorz.exotic_farm.user.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class DataSeed implements InitializingBean {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private SpiderRepository spiderRepository;
    @Autowired
    private CareRepository careRepository;
    @Autowired
    private SpiderSpeciesInfoRepository spiderSpeciesInfoRepository;
    @Autowired
    private MoltRepository moltRepository;

    private final SpeciesInfoDAO speciesInfoDAO = SpeciesInfoDAO.getInstance();

    @Override
    public void afterPropertiesSet(){
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(Role.ADMIN));
            roleRepository.save(new Role(Role.USER));
        }
        addUsers();
        addFarms();
        addSpiderInfo();
        addSpiders();
    }


    private void addUsers(){
        if(userRepository.count()!=0){
            return;
        }
        final Role admin = roleRepository.findByRoleName(Role.ADMIN);
        final Role user = roleRepository.findByRoleName(Role.USER);
        User admin1 = new User("admin",
                "a@a.pl",
                passwordEncoder.encode("admin"),
                "Aaa",
                "Aaa",
                "Szczecin");
        admin1.addRole(admin);
        userRepository.save(admin1);
        User user1 = new User("user",
                "u@u.pl",
                passwordEncoder.encode("user"),
                "Uuu",
                "Uuu",
                "Warszawa");
        user1.addRole(user);
        userRepository.save(user1);
    }

    public void addFarms(){
        if(farmRepository.count()!=0){
            return;
        }
        User user = userRepository.findByEMail("a@a.pl").orElse(null);
        Set<User> admins = new HashSet<>();
        admins.add(user);
        Farm farm = new Farm("AutoAddedFarm", new HashSet<>(), admins);
        farmRepository.save(farm);
    }

    public void addSpiders(){
        if(spiderRepository.count() != 0){
            return;
        }
        Farm farm = farmRepository.findByName("AutoAddedFarm").orElse(null);
        SpiderSpeciesInfo speciesInfo = spiderSpeciesInfoRepository.findByGenusAndSpecies("Brachypelma", "Hamorii").orElse(null);

        Spider spider1 = new Spider( "Kędzior", LocalDate.now(),Sex.FEMALE,Status.OWNED,Temperament.DOCILE, speciesInfo, farm, VenomPotency.LOW, Type.TERRESTRIAL);
        Spider spider2 = new Spider("Gienka", LocalDate.now(), Sex.FEMALE,Status.OWNED,Temperament.DOCILE,  speciesInfo, farm, VenomPotency.LOW, Type.TERRESTRIAL);
        Spider spider3 = new Spider("Laska", LocalDate.now(), Sex.FEMALE,Status.OWNED,Temperament.DOCILE, speciesInfo, farm, VenomPotency.LOW, Type.TERRESTRIAL);

        spider1.setFarm(farm);
        spider2.setFarm(farm);
        spider3.setFarm(farm);

        spiderRepository.save(spider1);
        spiderRepository.save(spider2);
        spiderRepository.save(spider3);

    }

    public void addSpiderInfo(){
        if(spiderSpeciesInfoRepository.count() !=0){
            return;
        }
        final List<SpeciesInfoFromFileDTO> speciesInfoList = speciesInfoDAO.getSpeciesInfoList();
        for (SpeciesInfoFromFileDTO info: speciesInfoList) {
            spiderSpeciesInfoRepository.save(SpiderSpeciesInfo.applyDTO(info));
        }
    }

}

