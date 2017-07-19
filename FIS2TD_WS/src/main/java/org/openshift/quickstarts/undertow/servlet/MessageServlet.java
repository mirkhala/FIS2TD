/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.openshift.quickstarts.undertow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * @author Stuart Douglas
 */
public class MessageServlet extends HttpServlet {
	
	private  String[] products={"Monitor", "Keyboard", "Memory", "SSD"};

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String oid = req.getParameter("orderid");
        Random rand = new Random();
        Gson gson = new Gson();
        
        
        Order o = new Order(oid);
        o.setAmount(rand.nextInt(100)+1);
        o.setProductPrice(BigDecimal.valueOf(rand.nextDouble()*100).setScale(2,RoundingMode.HALF_UP).doubleValue());
        o.setTotal(BigDecimal.valueOf(o.getAmount()*o.getProductPrice()).setScale(2,RoundingMode.HALF_UP).doubleValue());
        o.setProduct(products[rand.nextInt(products.length-1)]);
        
    	PrintWriter writer = resp.getWriter();
        writer.write(gson.toJson(o));
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
