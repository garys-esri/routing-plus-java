/*******************************************************************************
 * Copyright 2014 Esri
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ******************************************************************************/
package com.esri.wdc.routingplus;

import com.esri.core.map.Graphic;

public class Stop {
    
    private final int position;
    private final Graphic graphic;
    
    private boolean enabled = true;
    private String label;

    public Stop(int position, Graphic graphic) {
        this.position = position;
        this.graphic = graphic;
    }

    /**
     * @return the graphic
     */
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the id
     */
    public int getId() {
        return position;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    
}
