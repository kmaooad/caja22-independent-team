package edu.kmaooad.service;

import edu.kmaooad.repository.SkillSetRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SkillSetServiceTest {
    @Mock
    private SkillSetRepository skillSetRepository;
    @Mock
    private SkillService skillService;
    @InjectMocks
    private SkillSetService skillSetService;

    //TODO: Write later
}
