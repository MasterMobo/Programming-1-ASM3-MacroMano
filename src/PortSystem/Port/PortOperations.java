package PortSystem.Port;

import PortSystem.Containers.Container;

public interface PortOperations {
    double getDist(Port other);
    boolean canAddContainer(Container c);
    void addContainer(Container c);
    void removeContainer(Container c);

}
