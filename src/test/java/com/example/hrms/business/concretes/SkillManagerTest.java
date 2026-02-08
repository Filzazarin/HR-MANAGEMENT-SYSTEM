package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.SkillDao;
import com.example.hrms.entities.concretes.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkillManagerTest {

    @Mock
    private SkillDao skillDao;

    @InjectMocks
    private SkillManager skillManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Skill skill1 = new Skill();
        skill1.setId(1);
        Skill skill2 = new Skill();
        skill2.setId(2);

        when(skillDao.findAll()).thenReturn(Arrays.asList(skill1, skill2));

        DataResult<List<Skill>> result = skillManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all skills.", result.getMessage());
        verify(skillDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        Skill skill = new Skill();
        skill.setId(1);

        when(skillDao.save(skill)).thenReturn(skill);

        Result result = skillManager.add(skill);

        assertTrue(result.isSuccess());
        assertEquals("Added skill.", result.getMessage());
        verify(skillDao, times(1)).save(skill);
    }
}
