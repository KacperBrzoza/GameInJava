package com.example.Main.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ip_table", schema = "public", catalog = "pvjfjkvx")
public class IpTable
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ipid")
    private int ipid;
    @Basic
    @Column(name = "ip_address")
    private String ipAddress;

    public int getIpid()
    {
        return ipid;
    }

    public void setIpid(int ipid)
    {
        this.ipid = ipid;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpTable ipTable = (IpTable) o;
        return ipid == ipTable.ipid && Objects.equals(ipAddress, ipTable.ipAddress);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ipid, ipAddress);
    }
}
