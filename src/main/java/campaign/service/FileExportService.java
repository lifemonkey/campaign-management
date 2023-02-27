package campaign.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileExportService {


    public FileExportService() {
    }

    public Resource exportFileAsResource(String name, String type) {
        return null;
    }

}
