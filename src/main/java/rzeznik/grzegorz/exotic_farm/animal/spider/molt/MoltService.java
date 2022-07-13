package rzeznik.grzegorz.exotic_farm.animal.spider.molt;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoltService {

    private MoltRepository moltRepository;

    public MoltService(MoltRepository moltRepository) {
        this.moltRepository = moltRepository;
    }

    public List<MoltDTO> findAllBySpiderId(Integer id){
        return moltRepository.findAllBySpiderId(id).stream().map(Molt::toDTO).collect(Collectors.toList());
    }
}
