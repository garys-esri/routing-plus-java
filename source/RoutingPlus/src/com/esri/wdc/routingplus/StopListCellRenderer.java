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

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class StopListCellRenderer implements ListCellRenderer<Stop> {    

    @Override
    public Component getListCellRendererComponent(JList<? extends Stop> list, Stop value, int index, boolean isSelected, boolean cellHasFocus) {
        JCheckBox box = new JCheckBox(value.getId() + ": " + value.getLabel(), value.isEnabled());
        return box;
    }
}
