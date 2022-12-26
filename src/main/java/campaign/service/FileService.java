package campaign.service;

import campaign.domain.Campaign;
import campaign.domain.File;
import campaign.repository.CampaignRepository;
import campaign.repository.FileRepository;
import campaign.service.dto.FileDTO;
import campaign.service.mapper.FileMapper;
import campaign.web.rest.vm.FileVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final CampaignRepository campaignRepository;

    private final FileMapper fileMapper;

    public FileService(FileRepository fileRepository, CampaignRepository campaignRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.campaignRepository = campaignRepository;
        this.fileMapper = fileMapper;
    }

    @Transactional(readOnly = true)
    public FileDTO getFileById(Long id) {
        return fileMapper.fileToFileDTO(fileRepository.findById(id).orElse(new File()));
    }

    @Transactional(readOnly = true)
    public Page<FileDTO> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable).map(FileDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO createFile(FileVM fileVM) {
        File file = fileMapper.fileVMToFile(fileVM);

        if (file != null) {
            fileRepository.save(file);
            return fileMapper.fileToFileDTO(file);
        }

        return new FileDTO();
    }

    public String uploadFile(MultipartFile file) {
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            Files.write(path, bytes);

            File savedFile = fileRepository.save(new File(file.getOriginalFilename(), file.getName(), 1, "", file.getBytes()));
            return "Success";

        } catch (IOException e) {
            log.error("Could not upload file to server! ", e.getMessage());
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO updateFile(Long id, FileVM fileVM) {
        File file = fileMapper.fileVMToFile(fileVM);
        file.setId(id);

        fileRepository.save(file);
        return fileMapper.fileToFileDTO(file);
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
            return fileMapper.fileToFileDTO(file);
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
