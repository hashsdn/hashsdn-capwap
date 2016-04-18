/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.msgelements.*;
import org.opendaylight.capwap.msgelements.subelem.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by flat on 17/04/16.
 */
public class DescriptorTester {

    public ByteBuf ACDescriptorTest() {
        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        //create  Discovery Type
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);

        //create AcDescriptor element
        acDescriptor = new ACDescriptor();
        acDescriptor.setStations(2).
                setActiveWtps(2).
                setLimit(12).
                setMaxWtps(3).
                setSecuritySbit().
                setRmac((byte) 2).
                setDtlsPolicyDbit();
        //Now create Information Sub Element
        ACInformationSubElement e = new ACInformationSubElement(64);
        byte[] dummy = new byte[64];
        e.setAcInfoVendorId(12);
        e.setAcInfoType(35);
        e.setAcInfoData(dummy);

        acDescriptor.addAcInformationSubElem(e);

        ACInformationSubElement e1 = new ACInformationSubElement(128);
        byte[] dummy1 = new byte[128];
        e1.setAcInfoVendorId(102);
        e1.setAcInfoType(3);
        e1.setAcInfoData(dummy1);
        acDescriptor.addAcInformationSubElem(e1);

        //Add message element to Capwap Message
        msg.ctrlMsg.addMessageElement(acDescriptor);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf WtpDesciptorTester() {
        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        //create  Discovery Type
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);

        //create WtpDescriptor
        WtpDescriptor wtpDescriptor = new WtpDescriptor();
        wtpDescriptor.setMaxRadios((short) 12).
                setRadioInUse((short) 4);
        EncryptionSubElement e = new EncryptionSubElement((byte) 1, 12);
        EncryptionSubElement e1 = new EncryptionSubElement((byte) 2, 10);

        wtpDescriptor.addEncryptSubElement(e);
        wtpDescriptor.addEncryptSubElement(e1);

        DescriptorSubElement d = new DescriptorSubElement(128);
        d.setVendorId(120).setDescType(13);

        DescriptorSubElement d1 = new DescriptorSubElement(64);
        d1.setVendorId(110).setDescType(10);

        wtpDescriptor.addDescriptorSubElm(d);
        wtpDescriptor.addDescriptorSubElm(d1);

        msg.ctrlMsg.addMessageElement(wtpDescriptor);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf WtpBoardDataTester(){

        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        //create a Board data Dec
        WtpBoardDataMsgElem e = new WtpBoardDataMsgElem();
        e.setVendorId(12);
        BoardDataSubElem se = new BoardDataSubElem(12,64);
        e.addBoardData(se);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DeleteMacAclEntryTester(){

        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        DeleteMacAclEntry e= new DeleteMacAclEntry();
        e.setMacAddrLength((short)6);
        MacAddress mac = new MacAddress((short)6);
        e.addMacAddress(mac);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DeleteStationTester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        MacAddress mac = new MacAddress((short)8);
        mac.address[0]=12;
        mac.address[1]=15;
        mac.address[2]=12;
        mac.address[3]=17;
        mac.address[4]=12;
        mac.address[7]=12;
        DeleteStation e= new DeleteStation((short)12,mac);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }


    public ByteBuf DecryptionErrorReportTester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        MacAddress mac = new MacAddress((short)8);
        mac.address[0]=12;
        mac.address[1]=15;
        mac.address[2]=12;
        mac.address[3]=17;
        mac.address[4]=12;
        mac.address[7]=12;
        DecryptionErrorReport e= new DecryptionErrorReport();
        e.setRadioId((short)1);
        e.addMacAddress(mac);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf ControlIPV4Tester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        IPV4Address ipv4 = new IPV4Address();
        byte[] address = new byte[]{1, 2, 3, 4};
        ipv4.setAddress(address);

        CapwapControlIPV4Addr ctrl = new CapwapControlIPV4Addr();
        ctrl.setIpv4(ipv4);
        ctrl.setWtpCount(23333);


        msg.ctrlMsg.addMessageElement(ctrl);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf LocalIPV4Tester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        IPV4Address ipv4 = new IPV4Address();
        byte[] address = new byte[]{1, 2, 3, 4};
        ipv4.setAddress(address);

        CapwapLocalIPV4Address ctrl = new CapwapLocalIPV4Address();
        ctrl.setAddress(address);

        msg.ctrlMsg.addMessageElement(ctrl);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }
    public ByteBuf LocalIPV6Tester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        IPV6Address ipv6 = new IPV6Address();
        byte[] address = new byte[]{0,1, 2, 3, 4,6,7,8,9,10,11,12,13,14,15,16};
        ipv6.setAddress(address);

        CapwapLocalIPV6Address ctrl = new CapwapLocalIPV6Address();
        ctrl.setAddress(address);

        msg.ctrlMsg.addMessageElement(ctrl);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf CapwapTimerTester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        CapwapTimers timers = new CapwapTimers();
        timers.setDiscovery((short)255).setEchoReq((short)0);
        msg.ctrlMsg.addMessageElement(timers);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf CapwapProtocolTester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        CapwapTransportProtocol protocol = new CapwapTransportProtocol();
        protocol.setProtocol((byte) 2);
        msg.ctrlMsg.addMessageElement(protocol);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DataTransferDataTester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DataTransferData data = new DataTransferData();
        data.setDataMode((short) 2).setDataType((short) 5);
        byte [] dummy = new byte [128];
        data.setData(dummy);
        msg.ctrlMsg.addMessageElement(data);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DataTransferModeTester(){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DataTransferMode mode = new DataTransferMode();
        mode.setMode((byte)2);
        msg.ctrlMsg.addMessageElement(mode);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }
    public ByteBuf DecryptionErrorReportPeriodTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DecryptionErrorPeriod period = new DecryptionErrorPeriod();
        period.setRadioId((short)12).setReportInterval(36);
        msg.ctrlMsg.addMessageElement(period);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DuplicateIPV4Tester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DuplicateIPV4Addr duplicateIPV4Addr = new DuplicateIPV4Addr();
        //set IPV4 Adddress
        IPV4Address ipv4 = new IPV4Address();
        byte[] address = new byte[]{1, 2, 3, 4};
        ipv4.setAddress(address);

        //set
        MacAddress mac = new MacAddress((short)8);
        mac.address[0]=12;
        mac.address[1]=15;
        mac.address[2]=12;
        mac.address[3]=17;
        mac.address[4]=12;
        mac.address[7]=12;
        duplicateIPV4Addr.setIpv4(ipv4).setMacAddress(mac).setStatus((short) 1);
        msg.ctrlMsg.addMessageElement(duplicateIPV4Addr);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf IdleTimeOutTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        IdleTimeOut timeOut = new IdleTimeOut();
        timeOut.setTimout(12345);

        msg.ctrlMsg.addMessageElement(timeOut);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf EcnTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ECNSupport ecn =new ECNSupport();
        ecn.setEcn((byte) 1);
        msg.ctrlMsg.addMessageElement(ecn);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf imageDataTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ImageData img = new ImageData();
        byte [] dummy = new byte [1024];
        img.setData(dummy);
        img.setDataType((byte) 5);
        msg.ctrlMsg.addMessageElement(img);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf imageIdentifierTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ImageIdentifier id = new ImageIdentifier();
        byte [] dummy = new byte [1024];
        id.setData(dummy);
        id.setVendorID( 5);
        msg.ctrlMsg.addMessageElement(id);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf imageInfoTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ImageInformation id = new ImageInformation();
        byte [] dummy = new byte [16];
        id.setFileSize(12345);
        byte[] address = new byte[]{0,1, 2, 3, 4,6,7,8,9,10,11,12,13,14,15,16};
        id.setHash(address );
        msg.ctrlMsg.addMessageElement(id);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf initiateDownloadTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        InitiateDownload  in = new InitiateDownload();
        msg.ctrlMsg.addMessageElement(in);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }
    public ByteBuf locationDataTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        LocationData  l = new LocationData();
        byte[] address = new byte[]{0,1, 2, 3, 4,6,7,8,9,10,11,12,13,14,15,16};
        l.setLocationData(address);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf maxLenTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        MaxMsgLength l = new MaxMsgLength();
        l.setMaxLength(32000);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf radioAdminStateTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        RadioAdministrativeState l = new RadioAdministrativeState();
        l.setRadioId((short) 1);
        l.setAdminState((short) 2);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf radioOperationalStateTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        RadioOperationalState l = new RadioOperationalState();
        l.setRadioId((short) 1);
        l.setCause((short) 7);
        l.setState((short) 5);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }


    public ByteBuf resultCodeTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ResultCode l = new ResultCode();
        l.setResultCode(12345);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }
    public ByteBuf returnedMsgElementTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ReturnedMessageElement r = new ReturnedMessageElement();
        r.setReason((short) 1);
        byte[] address = new byte[]{0,1, 2, 3, 4,6,7,8,9,10,11,12,13,14,15,16};
        r.setMsgElement(address);

        msg.ctrlMsg.addMessageElement(r);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf sessionIdTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        SessionID sess = new SessionID();
        byte[] address = new byte[16];
        address[12] = 32;
        address[1] = 32;
        address[5] = 32;
        address[2] = 32;
        address[8] = 62;
        address[7] = 32;
        sess.setSessionid(address);

        msg.ctrlMsg.addMessageElement(sess);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

        public ByteBuf VendorSpecificPayloadTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();
        VendorSpecificPayload vsa = new VendorSpecificPayload();
            vsa.setVendorId(119);
        vsa.setElementID(120);
        byte[] address = new byte[512];
        address[12] = 32;
        address[1] = 32;
        address[5] = 32;
        address[2] = 32;
        address[8] = 62;
        address[7] = 32;

        vsa.setData(address);

        msg.ctrlMsg.addMessageElement(vsa);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }
        public ByteBuf statisticsTimerTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();
        StatisticsTimer timer = new StatisticsTimer();
        timer.setTimer(320);

        msg.ctrlMsg.addMessageElement(timer);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf frameTunnelModeTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();
        WtpFrameTunnelMode ft = new WtpFrameTunnelMode();
        ft.seteBit();
        ft.setnBit();
        ft.setrBit();
        msg.ctrlMsg.addMessageElement(ft);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.setHlen(8);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        return buf;
    }

    public ByteBuf fallBackModeTester (){

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();
        WtpFallBack fb = new WtpFallBack();
        msg.ctrlMsg.addMessageElement(fb);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        //msg.header.setHlen(8);

        msg.header.setMbit();
        MacAddress mac = new MacAddress();
        byte[] address = new byte[6];
        address[0] = 12;
        address[1] = 52;
        address[2] = 32;
        address[3] = 42;
        address[4] = 72;
        address[5] = 92;

        mac.setAddress(address);
        msg.header.setRadioMacAddress(mac);

        WsiInfo wsi = new WsiInfo();
        msg.header.setWbit();

        byte[] wsiData = new byte[8];
        address[0] = 12;
        address[1] = 52;
        address[2] = 32;
        address[3] = 42;
        address[4] = 72;
        address[5] = 92;

        wsi.setData(wsiData);
        msg.header.setWsiInfo(wsi);


        msg.header.encodeHeader2(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }














    public void sender(ByteBuf buf) {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress address = null;
        try {
            address = InetAddress.getLoopbackAddress();
            System.out.println(address);
        } catch (Exception e) {
            e.printStackTrace();
        }

        {
            System.out.printf("Capacity of ByteBuf  %d", buf.writableBytes());
            System.out.printf("Writer Index %d", buf.writerIndex());
            int packet_size = buf.writerIndex();
            System.out.printf("packetsize %d", packet_size);
            byte[] array = buf.array();
            DatagramPacket packet = new DatagramPacket(array, packet_size, address, 5246);
            try {
                socket.send(packet);
                System.out.printf("\nSending Capwap Message  ");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String args[]) {


        DescriptorTester tester = new DescriptorTester();
        ByteBuf buf = null;

        //buf = tester.ACDescriptorTest();
        //tester.sender(buf);

        //buf =  tester.WtpDesciptorTester();
        //tester.sender(buf);

        // buf =  tester.WtpBoardDataTester();
        //tester.sender(buf);

       //  buf =  tester.DeleteMacAclEntryTester();
        //tester.sender(buf);

        //buf =  tester.DeleteStationTester();
        //tester.sender(buf);

        //buf =  tester.DecryptionErrorReportTester();
        //tester.sender(buf);

        //buf = tester.ControlIPV4Tester();
        //tester.sender(buf);

        //buf = tester.LocalIPV4Tester();
        //tester.sender(buf);

        //buf = tester.LocalIPV6Tester();
        //tester.sender(buf);

        //buf = tester.CapwapTimerTester();
        //tester.sender(buf);

        //buf = tester.CapwapProtocolTester();
        //tester.sender(buf);

        //buf = tester.DataTransferDataTester();
        //tester.sender(buf);

        //buf = tester.DataTransferModeTester();
        //tester.sender(buf);

        //buf = tester.DecryptionErrorReportPeriodTester();
        //tester.sender(buf);


        //buf = tester.DuplicateIPV4Tester();
        //tester.sender(buf);

        //buf = tester.IdleTimeOutTester();
        //tester.sender(buf);

       // buf = tester.EcnTester();
        //tester.sender(buf);

        //buf = tester.imageDataTester();
        //tester.sender(buf);

        //buf = tester.imageIdentifierTester();
        // tester.sender(buf);
        //buf = tester.imageInfoTester();
        //tester.sender(buf);

        //buf = tester.initiateDownloadTester();
        //tester.sender(buf);

       // buf = tester.locationDataTester();
        //tester.sender(buf);

       // buf = tester.maxLenTester();
        //tester.sender(buf);

        //buf = tester.radioAdminStateTester();
        //tester.sender(buf);

        //buf = tester.radioOperationalStateTester();
        //tester.sender(buf);

       // buf = tester.resultCodeTester();
        //tester.sender(buf);

        //buf = tester.returnedMsgElementTester();
        //tester.sender(buf);

        //buf = tester.sessionIdTester();
        //tester.sender(buf);

       // buf = tester.statisticsTimerTester();
        //tester.sender(buf);

        //buf = tester.VendorSpecificPayloadTester();
        //tester.sender(buf);

       // buf = tester.frameTunnelModeTester();
        //tester.sender(buf);
        buf = tester.fallBackModeTester();
        tester.sender(buf);
    }
}