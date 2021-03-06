/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.examples.machinereassignment.domain;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.drools.planner.examples.common.domain.AbstractPersistable;

@XStreamAlias("MrMachine")
public class MrMachine extends AbstractPersistable {

    private MrNeighborhood neighborhood;
    private MrLocation location;

    private Map<MrResource, MrMachineCapacity> machineCapacityMap;
    private Map<MrMachine, MrMachineMoveCost> machineMoveCostMap;

    public MrNeighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(MrNeighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public MrLocation getLocation() {
        return location;
    }

    public void setLocation(MrLocation location) {
        this.location = location;
    }

    public Map<MrResource, MrMachineCapacity> getMachineCapacityMap() {
        return machineCapacityMap;
    }

    public void setMachineCapacityMap(Map<MrResource, MrMachineCapacity> machineCapacityMap) {
        this.machineCapacityMap = machineCapacityMap;
    }

    public MrMachineCapacity getMachineCapacity(MrResource resource) {
        return machineCapacityMap.get(resource);
    }

    public Map<MrMachine, MrMachineMoveCost> getMachineMoveCostMap() {
        return machineMoveCostMap;
    }

    public void setMachineMoveCostMap(Map<MrMachine, MrMachineMoveCost> machineMoveCostMap) {
        this.machineMoveCostMap = machineMoveCostMap;
    }

    public String getLabel() {
        return "Machine " + getId();
    }

    public int getMoveCostTo(MrMachine toMachine) {
        return machineMoveCostMap.get(toMachine).getMoveCost();
    }

}
