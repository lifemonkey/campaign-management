package campaign.service;

import campaign.domain.Campaign;
import campaign.domain.File;
import campaign.repository.CampaignRepository;
import campaign.repository.FileRepository;
import campaign.service.dto.FileDTO;
import campaign.service.mapper.FilesMapper;
import campaign.web.rest.vm.FileVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final CampaignRepository campaignRepository;

    private final FilesMapper filesMapper;

    public FileService(FileRepository fileRepository, CampaignRepository campaignRepository, FilesMapper filesMapper) {
        this.fileRepository = fileRepository;
        this.campaignRepository = campaignRepository;
        this.filesMapper = filesMapper;
    }

    @Transactional(readOnly = true)
    public FileDTO getFileById(Long id) {
        return filesMapper.fileToFileDTO(fileRepository.findById(id).orElse(new File()));
    }

    @Transactional(readOnly = true)
    public Page<FileDTO> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable).map(FileDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO createFile(FileVM fileVM) {
        File file = filesMapper.fileVMToFile(fileVM);

        if (file != null) {
            fileRepository.save(file);
            return filesMapper.fileToFileDTO(file);
        }

        return new FileDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO updateFile(Long id, FileVM fileVM) {
        File file = filesMapper.fileVMToFile(fileVM);
        file.setId(id);

        fileRepository.save(file);
        return filesMapper.fileToFileDTO(file);
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO updateFileCampaign(Long id, Long campaignId) {
        Optional<File> fileOpt = fileRepository.findById(id);
        Optional<Campaign> campaignOpt = campaignRepository.findById(campaignId);

        if (fileOpt.isPresent() && campaignOpt.isPresent()) {
            File file = fileOpt.get();
            // link campaign to file
            file.setCampaign(campaignOpt.get());
            fileRepository.save(file);
            return filesMapper.fileToFileDTO(file);
        }

        return new FileDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long id) {
        Optional<File> fileOpt = fileRepository.findById(id);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            file.setCampaign(null);
            fileRepository.save(file);
            fileRepository.delete(file);
        }
    }
}
