/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.emrapi.conditionlist.dao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.module.emrapi.conditionlist.domain.Condition;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HibernateConditionDAOIT extends BaseModuleContextSensitiveTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    ConditionDAO conditionDao;
    @Autowired
    PatientService patientService;

    @Before
    public void setUp() throws Exception {
        executeDataSet("conditionListDataSet.xml");
    }

    @Test
    public void shouldGetConditionByUuid() {
        Condition condition = conditionDao.getConditionByUuid("2cc6880e-2c46-11e4-9038-a6c5e4d22fb7");
        assertEquals(Condition.Status.CONFIRMED, condition.getStatus());
        assertEquals("Tuberculosis", condition.getConcept().getName().getName());
        assertEquals("2015-01-12 00:00:00.0", condition.getDateCreated().toString());
    }

    @Test
    public void shouldGetConditionsForPatient() {
        Patient patient = patientService.getPatient(1);
        List<Condition> conditionsForPatient = conditionDao.getConditionsByPatient(patient);
        assertEquals(4, conditionsForPatient.size());
    }

    @Test
    public void shouldGetActiveConditionsForPatient() {
        Patient patient = patientService.getPatient(3);
        List<Condition> activeConditions = conditionDao.getActiveConditions(patient);
        assertEquals(2, activeConditions.size());
    }
}