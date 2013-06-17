package org.vaadin.chronographer.gwt.client.connect;

import com.vaadin.shared.communication.ServerRpc;

public interface ChronoGrapherServerRpc extends ServerRpc {
    void onClick(int id, int x, int y);
}
