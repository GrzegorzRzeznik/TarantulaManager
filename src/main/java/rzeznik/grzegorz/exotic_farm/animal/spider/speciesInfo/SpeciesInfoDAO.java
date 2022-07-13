package rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SpeciesInfoDAO {

    private static SpeciesInfoDAO INSTANCE;
    private final List<SpeciesInfoFromFileDTO> speciesInfoFromFileList = populateList();

    private SpeciesInfoDAO() {
    }

    public static SpeciesInfoDAO getInstance() {
        if (INSTANCE == null) {
            synchronized (SpeciesInfoDAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpeciesInfoDAO();
                }
            }
        }
        return INSTANCE;
    }

    public List<SpeciesInfoFromFileDTO> getSpeciesInfoList(){
        return speciesInfoFromFileList;
    }

    private List<SpeciesInfoFromFileDTO> populateList(){
        List<String> infoList = getInfoFromFile();
        List<SpeciesInfoFromFileDTO> speciesInfoFromFileDTOList = new ArrayList<>();
        for (String line : infoList) {
            speciesInfoFromFileDTOList.add(SpeciesInfoFromFileDTO.applyFromText(line));
        }
        return speciesInfoFromFileDTOList;
    }


    private List<String> getInfoFromFile(){
        List<String> textLines;
        List<String> infoList = new ArrayList<>();
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resourceURL = classLoader.getResource("spiderData.txt");

        try {
            textLines = Files.readAllLines(Paths.get(resourceURL.toURI()));
            String genus = "";
            for (String line: textLines) {
                if (!line.startsWith("\t") && !line.startsWith(" ")) {
                    genus = line;
                } else {
                    infoList.add(genus +","+ line.trim());
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return infoList;
    }
}
