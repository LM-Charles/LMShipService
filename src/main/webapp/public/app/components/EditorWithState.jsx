/**
 * Created by desmondz on 7/26/2015.
 */
var EditorWithState = React.createClass({
    getInitialState(){
        return {
            showUpdateOrderStatus: false,
            parameter: {
                orderId: null
            }
        };
    },
    closeUpdateOrderStatus(){
        this.setState({
            showUpdateOrderStatus: false,
        });
    },
    updateOrderStatusConfirm(){
        var result = new AjaxClient().ajaxUpdateStatus(this.state.order.id, this.refs.newStatus.getValue(), this.refs.newStatusDescription.getValue());
        this.onClickQueryOrder();
        this.closeUpdateOrderStatus();
    },
    openUpdateOrderStatus(){
        this.setState({
            showUpdateOrderStatus: true
        });
    },
    onClickQueryOrder(){
        var result = new AjaxClient().ajaxLoadOrder(this.refs.orderId.getValue());
        console.log(JSON.stringify(result));
        this.setState({
            order: result
        })
    },
    onClickUpdateTracking(sequence){
        var result = new AjaxClient().ajaxSaveTracking(this.state.order.id, this.state.order.shipments[sequence].id, this.refs["tracking" + sequence].getValue());
        this.onClickQueryOrder();
    },
    onClickUpdateDimension(sequence){
        var result = new AjaxClient().ajaxUpdateDimension(this.state.order.id, this.state.order.shipments[sequence].id,
            this.refs["length" + sequence].getValue(),
            this.refs["width" + sequence].getValue(),
            this.refs["height" + sequence].getValue(),
            this.refs["packaging" + sequence].getValue(),
            this.refs["weight" + sequence].getValue()
        );
        this.onClickQueryOrder();
    },
    generateShipmentLines(){
        var rows = [];
        if (this.state.order == null) {
            return [];
        }
        for (var i = 0; i < this.state.order.shipments.length; i++) {
            console.log("GO " + i + " " + this.state.order.shipments[i]);
            var rowHeader = ">> Shipment #" + i;
            rows.push(
                <Row>
                    <Col mdOffset="1" md="11">
                        <div>
                            <Panel collapsible header={rowHeader} eventKey="1">
                        <textarea rows="20" value={JSON.stringify(this.state.order.shipments[i], null, 4)}>

                        </textarea>
                                <Input label='Update Tracking Number'>
                                    <Input type='text' ref={'tracking' + i} placeholder='Enter Tracking Number'/>
                                    <Button onClick={this.onClickUpdateTracking.bind(null, i)}>Update Tracking</Button>
                                </Input>
                                <Input label='Update Dimension'>
                                    <Row>
                                        <Col md="4"><Input ref={'length' + i} type='text' placeholder='Length'/></Col>
                                        <Col md="4"><Input ref={'width' + i} type='text' placeholder='Width'/></Col>
                                        <Col md="4"><Input ref={'height' + i} type='text' placeholder='Height'/></Col>
                                    </Row>
                                    <Row>
                                        <Col md="12">
                                            <Input ref={'packaging' + i} type='select' placeholder='Packaging'>
                                                <option value='CUSTOM'>Self Package</option>
                                                <option value='SMALL'>Small Box</option>
                                                <option value='MEDIUM'>Medium Box</option>
                                                <option value='LARGE'>Large Box</option>
                                            </Input>
                                        </Col>
                                        <Col md="12"> <Input ref={'weight' + i} type='text' placeholder='Weight'/></Col>
                                    </Row>
                                    <Button onClick={this.onClickUpdateDimension.bind(null, i)}>Update
                                        Dimension</Button>
                                </Input>
                            </Panel>
                        </div>
                    </Col>
                </Row>
            )
        }
        return rows;
    },
    generateOrder(){
        if (this.state.order == null) {
            return null;
        }
        var orderId = this.state.order == null ? "??" : this.state.order.id;
        var order = this.state.order == null ? "Query for Order first" : deepClone(this.state.order);
        delete order.shipments;
        delete order.orderStatusModel;
        var orderStatusModel = this.state.order == null ? "Query for Order first" : this.state.order.orderStatusModel;
        var orderHeader = ">> Order Details for #" + orderId;

        return (
            <Row>
                <Col md="12">
                    <Panel collapsible header={{orderHeader}}>
                            <textarea value={JSON.stringify(order, null, 4)} rows="31">
                            </textarea>
                            <textarea value={JSON.stringify(orderStatusModel, null, 4)} rows="6">
                            </textarea>
                    </Panel>
                </Col>
            </Row>
        )
    },
    generateUpdateOrderModal(){
        var order = this.state.order == null ? "??" : this.state.order.id;

        return (
            <Modal show={this.state.showUpdateOrderStatus} onHide={this.closeUpdateOrderStatus}>
                <Modal.Header closeButton>
                    <Modal.Title>Update Order Status #{order}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Input ref="newStatus" label="New Status" type='select' placeholder="Select status">
                        <option value='ORDER_PLACED'>Pending Pickup</option>
                        <option value='PROCESSING'>Processing</option>
                        <option value='IN_TRANSIT'>In Transit</option>
                        <option value='ERROR'>Exception</option>
                        <option value='COMPLETE'>Complete</option>
                        <option value='CANCELLED'>Complete</option>
                    </Input>
                    <Input ref="newStatusDescription" label="Message" type='textarea'
                           placeholder='Describe your change'/>
                </Modal.Body>
                <Modal.Footer>
                    <Row>
                        <Col md="6"><Button onClick={this.closeUpdateOrderStatus}>Close</Button></Col>
                        <Col md="6"><Button bsStyle="primary"
                                            onClick={this.updateOrderStatusConfirm}>Save</Button></Col>
                    </Row>
                </Modal.Footer>
            </Modal>
        )
    },
    generateUpdateOrderButton(){
        var orderId = this.state.order == null ? "??" : this.state.order.id;

        return (
            <Col md="2"><Button className="fill" onClick={this.openUpdateOrderStatus}>Update Status
                #{orderId}...</Button></Col>
        )
    },
    render() {
        var queryShipmentButton = (
            <Button bsStyle="primary" onClick={this.onClickQueryOrder}>Query Order</Button>
        );


        return (
            <div className="component-EditorWithState-container">
                <h3>LM Administrative Website</h3>
                <Row>
                    <Col md="6"><Input type='text' label='Username'/></Col>
                    <Col md="6"><Input type='password' label='Password'/></Col>
                </Row>
                <Row>
                    <Col md="12"> <Input type='text' ref='orderId' label='Search for Order by ID'
                                         placeholder='Enter Order ID' buttonAfter={queryShipmentButton}/></Col>
                </Row>
                {this.generateOrder()}
                {this.generateShipmentLines()}
                <Row>
                    <Col md="12">
                        <Panel>
                            <Row>
                                <Col md="2"><Button className="fill">Re-calculate Rate</Button></Col>
                                {this.generateUpdateOrderButton()}
                                {this.generateUpdateOrderModal()}
                            </Row>
                        </Panel>
                    </Col>
                </Row>
            </div>
        )
    }
});