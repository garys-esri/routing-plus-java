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

 * Some of this code is from Esri's "Point-to-point routing" sample, which
 * carries the following statement:
 *
 * "Copyright 2014 Esri
 *
 * "All rights reserved under the copyright laws of the United States and
 * applicable international laws, treaties, and conventions.
 *
 * "You may freely redistribute and use this sample code, with or without
 * modification, provided you include the original copyright notice and use
 * restrictions.
 *
 * "See the use restrictions."
 ******************************************************************************/
package com.esri.wdc.routingplus;

import com.esri.core.geometry.Unit;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.TextSymbol;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;
import com.esri.core.tasks.na.NAFeaturesAsFeature;
import com.esri.core.tasks.na.Route;
import com.esri.core.tasks.na.RouteParameters;
import com.esri.core.tasks.na.RouteResult;
import com.esri.core.tasks.na.RouteTask;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.MapOptions;
import com.esri.runtime.ArcGISRuntime;
import com.esri.toolkit.overlays.DrawingCompleteEvent;
import com.esri.toolkit.overlays.DrawingCompleteListener;
import com.esri.toolkit.overlays.DrawingOverlay;
import java.awt.Color;
import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class RoutingPlusApp extends javax.swing.JFrame {
    
    private static final SimpleMarkerSymbol STOP_CIRCLE_SYMBOL =
            new SimpleMarkerSymbol(Color.BLUE, 25, SimpleMarkerSymbol.Style.CIRCLE);

    private final GraphicsLayer graphicsLayer;
    private final DrawingOverlay myDrawingOverlay;
    private final NAFeaturesAsFeature barriers = new NAFeaturesAsFeature();
    private final DefaultListModel<Stop> listModel = new DefaultListModel<>();
    private final RouteCache routeCache = new RouteCache();
    
    private int numStops = 0;
    private LoginDialog loginDialog = null;
    private LookupStopDialog lookupStopDialog = null;
    private int lastRouteGraphicId = Integer.MIN_VALUE;

    public RoutingPlusApp() {
        InputStream clientidStream = getClass().getResourceAsStream("clientid.txt");
        if (null != clientidStream) {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientidStream));
            try {
                ArcGISRuntime.setClientID(in.readLine());
            } catch (IOException ex) {
                Logger.getLogger(RoutingPlusApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        initComponents();

        jList_stops.setModel(listModel);

        graphicsLayer = new GraphicsLayer();
        map.getLayers().add(graphicsLayer);

        myDrawingOverlay = new DrawingOverlay();
        map.addMapOverlay(myDrawingOverlay);
        myDrawingOverlay.setActive(true);
        myDrawingOverlay.addDrawingCompleteListener(new DrawingCompleteListener() {
            @Override
            public void drawingCompleted(DrawingCompleteEvent arg0) {
                Graphic graphic = (Graphic) myDrawingOverlay.getAndClearFeature();
                addStop(graphic);
            }
        });
    }
    
    private void addStop(Graphic graphic) {
        addStop(graphic, null);
    }
    
    private void addStop(Graphic graphic, String label) {
        graphicsLayer.addGraphic(graphic);
        if (graphic.getAttributeValue("type").equals("Stop")) {
            numStops++;
            graphicsLayer.addGraphic(new Graphic(graphic.getGeometry(), new TextSymbol(12, String
                    .valueOf(numStops), Color.WHITE)));
            Stop stop = new Stop(numStops, graphic);
            stop.setLabel(null == label ? "Graphic " + numStops : label);
            listModel.addElement(stop);
            checkAndEnableSolveButton();
        } else if (graphic.getAttributeValue("type").equals("Barrier")) {
            barriers.addFeature(graphic);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton_addStop = new javax.swing.JButton();
        jButton_lookupStop = new javax.swing.JButton();
        jButton_addBarrier = new javax.swing.JButton();
        jButton_solve = new javax.swing.JButton();
        jProgressBar_solve = new javax.swing.JProgressBar();
        jButton_setLogin = new javax.swing.JButton();
        jButton_reset = new javax.swing.JButton();
        jCheckBox_preserveOrder = new javax.swing.JCheckBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel_driveTime = new javax.swing.JLabel();
        jScrollPane_stops = new javax.swing.JScrollPane();
        jList_stops = new javax.swing.JList<Stop>();
        jList_stops.setCellRenderer(new StopListCellRenderer());
        map = new JMap(new MapOptions(MapOptions.MapType.STREETS, 33, -80, 4));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Routing Plus");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton_addStop.setText("Add a Stop");
        jButton_addStop.setFocusable(false);
        jButton_addStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_addStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_addStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addStopActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_addStop);

        jButton_lookupStop.setText("Lookup Stop");
        jButton_lookupStop.setFocusable(false);
        jButton_lookupStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_lookupStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_lookupStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lookupStopActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_lookupStop);

        jButton_addBarrier.setText("Add a Barrier");
        jButton_addBarrier.setFocusable(false);
        jButton_addBarrier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_addBarrier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_addBarrier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addBarrierActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_addBarrier);

        jButton_solve.setText("Solve");
        jButton_solve.setEnabled(false);
        jButton_solve.setFocusable(false);
        jButton_solve.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_solve.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_solve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_solveActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_solve);

        jProgressBar_solve.setMaximumSize(new java.awt.Dimension(100, 14));
        jToolBar1.add(jProgressBar_solve);

        jButton_setLogin.setText("Set Login");
        jButton_setLogin.setFocusable(false);
        jButton_setLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_setLogin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_setLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_setLoginActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_setLogin);

        jButton_reset.setText("Reset");
        jButton_reset.setFocusable(false);
        jButton_reset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_reset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_reset);

        jCheckBox_preserveOrder.setSelected(true);
        jCheckBox_preserveOrder.setText("Preserve Order of Stops");
        jCheckBox_preserveOrder.setFocusable(false);
        jCheckBox_preserveOrder.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBox_preserveOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jCheckBox_preserveOrder);

        jLabel_driveTime.setText(" ");
        jLabel_driveTime.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel_driveTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel_driveTime.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jList_stops.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_stops.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_stopsMouseClicked(evt);
            }
        });
        jScrollPane_stops.setViewportView(jList_stops);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_driveTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane_stops, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_driveTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_stops, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);
        jSplitPane1.setRightComponent(map);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkAndEnableSolveButton() {
        int countEnabledStops = 0;
        Enumeration<Stop> elements = listModel.elements();
        while (elements.hasMoreElements()) {
            if (elements.nextElement().isEnabled()) {
                countEnabledStops++;
            }
        }
        jButton_solve.setEnabled(2 <= countEnabledStops);
    }
    
    private void jButton_addStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addStopActionPerformed
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("type", "Stop");
        myDrawingOverlay.setUp(
                DrawingOverlay.DrawingMode.POINT,
                STOP_CIRCLE_SYMBOL,
                attributes);
    }//GEN-LAST:event_jButton_addStopActionPerformed

    private void jButton_addBarrierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addBarrierActionPerformed
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("type", "Barrier");
        myDrawingOverlay.setUp(
                DrawingOverlay.DrawingMode.POINT,
                new SimpleMarkerSymbol(Color.BLACK, 16, SimpleMarkerSymbol.Style.X),
                attributes);
    }//GEN-LAST:event_jButton_addBarrierActionPerformed

    private void jButton_solveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_solveActionPerformed
        jProgressBar_solve.setIndeterminate(true);
        new Thread() {
            public void run() {
                doRouting();
            }
        }.start();
    }//GEN-LAST:event_jButton_solveActionPerformed

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        graphicsLayer.removeAll();
        barriers.clearFeatures();
        listModel.clear();
        numStops = 0;
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        map.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jList_stopsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_stopsMouseClicked
        int index = jList_stops.locationToIndex(evt.getPoint());
        Stop item = listModel.getElementAt(index);
        // Toggle selected state
        item.setEnabled(!item.isEnabled());
        // Repaint cell
        jList_stops.repaint(jList_stops.getCellBounds(index, index));
        
        checkAndEnableSolveButton();
    }//GEN-LAST:event_jList_stopsMouseClicked

    private void jButton_setLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_setLoginActionPerformed
        if (null == loginDialog) {
            loginDialog = new LoginDialog(this, true);
        }
        loginDialog.setVisible(true);
    }//GEN-LAST:event_jButton_setLoginActionPerformed

    private void jButton_lookupStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lookupStopActionPerformed
        if (null == lookupStopDialog) {
            lookupStopDialog = new LookupStopDialog(this, true, map.getSpatialReference(), new CallbackListener<List<LocatorGeocodeResult>>() {

                @Override
                public void onCallback(List<LocatorGeocodeResult> objs) {
                    if (null == objs || 0 == objs.size()) {
                        onError(new Exception("No results"));
                    } else {
                        Map<String, Object> attributes = new HashMap<>();
                        attributes.put("type", "Stop");
                        Graphic g = new Graphic(objs.get(0).getLocation(), STOP_CIRCLE_SYMBOL, attributes);
                        addStop(g, objs.get(0).getAddress());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    //It is unfortunate
                }
            });
            lookupStopDialog.setModalityType(Dialog.ModalityType.MODELESS);
        }
        lookupStopDialog.setVisible(true);
    }//GEN-LAST:event_jButton_lookupStopActionPerformed

    private void doRouting() {
        try {
            NAFeaturesAsFeature stops = new NAFeaturesAsFeature();
            List<Stop> stopsList = new ArrayList<Stop>();
            Enumeration<Stop> stopElements = listModel.elements();
            while (stopElements.hasMoreElements()) {
                Stop stop = stopElements.nextElement();
                if (stop.isEnabled()) {
                    stops.addFeature(stop.getGraphic());
                    stopsList.add(stop);
                }
            }
            
            RouteResult cachedResult = routeCache.get(stopsList);
            if (null != cachedResult) {
                showResult(cachedResult);
            } else {
                UserCredentials creds = null == loginDialog ? null : loginDialog.getUserCredentials();
                RouteTask task;
                if (null == creds) {
                    System.out.println("free");
                    task = RouteTask.createOnlineRouteTask("http://tasks.arcgisonline.com/ArcGIS/rest/services/NetworkAnalysis/ESRI_Route_NA/NAServer/Route", null);
                } else {
                    System.out.println("premium");
                    task = RouteTask.createOnlineRouteTask("https://route.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World", creds);
                }
                RouteParameters parameters = task.retrieveDefaultRouteTaskParameters();
                parameters.setOutSpatialReference(map.getSpatialReference());
                parameters.setOutputGeometryPrecision(10.0);
                parameters.setOutputGeometryPrecisionUnits(Unit.EsriUnit.FEET);
                stops.setSpatialReference(map.getSpatialReference());
                parameters.setStops(stops);
                parameters.setFindBestSequence(!jCheckBox_preserveOrder.isSelected()); // opposite of 'preserve order of stops' 
                if (barriers.getFeatures().size() > 0) {
                    barriers.setSpatialReference(map.getSpatialReference());
                    parameters.setPointBarriers(barriers);
                }
                jLabel_driveTime.setText(" ");
                RouteResult result = task.solve(parameters);
                routeCache.put(stopsList, result);
                showResult(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error has occured. " + e.getLocalizedMessage(), "", JOptionPane.WARNING_MESSAGE);
        } finally {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jProgressBar_solve.setIndeterminate(false);
                }
            });
        }
    }

    private void showResult(RouteResult result) {
        if (result != null) {
            // display the top route on the map as a graphic 
            Route topRoute = result.getRoutes().get(0);
            Graphic routeGraphic = new Graphic(topRoute.getRouteGraphic().getGeometry(),
                    new SimpleLineSymbol(Color.BLUE, 2.0f));
            if (Integer.MIN_VALUE < lastRouteGraphicId) {
                graphicsLayer.removeGraphic(lastRouteGraphicId);
            }
            lastRouteGraphicId = graphicsLayer.addGraphic(routeGraphic);
            double min = topRoute.getTotalMinutes();
            System.out.println(min + " total minutes");
            String displayHours = Integer.toString((int) Math.floor(min / 60));
            String displayMinutes = Integer.toString((int) Math.round(min % 60));
            if (2 > displayMinutes.length()) {
                displayMinutes = "0" + displayMinutes;
            }
            jLabel_driveTime.setText(displayHours + ":" + displayMinutes);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoutingPlusApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoutingPlusApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoutingPlusApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoutingPlusApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoutingPlusApp().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_addBarrier;
    private javax.swing.JButton jButton_addStop;
    private javax.swing.JButton jButton_lookupStop;
    private javax.swing.JButton jButton_reset;
    private javax.swing.JButton jButton_setLogin;
    private javax.swing.JButton jButton_solve;
    private javax.swing.JCheckBox jCheckBox_preserveOrder;
    private javax.swing.JLabel jLabel_driveTime;
    private javax.swing.JList<Stop> jList_stops;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar_solve;
    private javax.swing.JScrollPane jScrollPane_stops;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private com.esri.map.JMap map;
    // End of variables declaration//GEN-END:variables
}
