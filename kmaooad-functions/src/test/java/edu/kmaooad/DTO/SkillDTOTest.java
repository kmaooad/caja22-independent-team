package edu.kmaooad.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillDTOTest {
    SkillDTO skillDTO = new SkillDTO("1");

    @Test
    void testEquals() {
        assertEquals(skillDTO, new SkillDTO("1"));
        assertTrue(skillDTO.equals(new SkillDTO("1")));
    }

    @Test
    void testHashCode() {
        assertEquals(skillDTO.hashCode(), new SkillDTO("1").hashCode());
    }
}