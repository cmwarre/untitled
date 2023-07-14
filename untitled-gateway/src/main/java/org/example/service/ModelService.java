package org.example.service;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.example.model.Model;
import org.example.repository.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(ModelService.class);

    private final ModelRepo repo;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ModelService(ModelRepo repo, ApplicationEventPublisher eventPublisher) {
        this.repo = repo;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Model save(Model model) {
        Model savedModel = repo.save(model);
        eventPublisher.publishEvent(new ModelSavedEvent(savedModel));
        logger.infof("Thread ID " + Thread.currentThread().getId());
        return savedModel;
    }

    public List<Model> getAll() {
        return repo.findAll();
    }

    public Model getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public Model update(Long id, Model newModelData) {
        Optional<Model> optionalModel = repo.findById(id);
        if (optionalModel.isPresent()) {
            Model existingModel = optionalModel.get();
            existingModel.setName(newModelData.getName());
            return repo.save(existingModel);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public static class ModelSavedEvent extends ApplicationEvent {

        public ModelSavedEvent(Model source) {
            super(source);
        }

    }

}
